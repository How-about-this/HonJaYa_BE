package goorm.honjaya.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.board.exception.BoardNotFoundException;
import goorm.honjaya.domain.board.repository.BoardRepository;
import goorm.honjaya.domain.image.dto.ProfileImageDto;
import goorm.honjaya.domain.image.entity.BoardImage;
import goorm.honjaya.domain.image.entity.Image;
import goorm.honjaya.domain.image.entity.ProfileImage;
import goorm.honjaya.domain.image.exception.CannotDeleteKakaoImageException;
import goorm.honjaya.domain.image.exception.CannotDeletePrimaryImageException;
import goorm.honjaya.domain.image.exception.ProfileImageNotFoundException;
import goorm.honjaya.domain.image.repository.BoardImageRepository;
import goorm.honjaya.domain.image.repository.ProfileImageRepository;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.exception.UserNotFountException;
import goorm.honjaya.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageService {


    private final ProfileImageRepository profileImageRepository;
    private final BoardImageRepository boardImageRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional(readOnly = true)
    public List<ProfileImageDto> findProfileImages(Long userId) {
        return profileImageRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(ProfileImageDto::from)
                .collect(Collectors.toList());
    }

    @Transactional // 이 경우
    public List<ProfileImageDto> saveProfileImages(Long userId, List<MultipartFile> multipartFile) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFountException::new);

        if (multipartFile != null) {
            return multipartFile.stream()
                    .map(image -> (ProfileImage) saveImage(image, "profile", user, null))
                    .map(ProfileImageDto::from)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList(); // null인 경우 빈 리스트 반환
    }

    @Transactional
    public List<BoardImage> saveBoardImages(Long boardId, List<MultipartFile> multipartFile){

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        if (multipartFile != null) {
            List<BoardImage> boardImages = multipartFile.stream()
                    .map(image -> (BoardImage) saveImage(image, "board", null, board))
                    .collect(Collectors.toList());

            return boardImages;
        }
        return Collections.emptyList(); // null인 경우 빈 리스트 반환
    }

    @Transactional
    public List<BoardImage> modifyBoardImage(Long boardId, List<MultipartFile> multipartFile){

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        List<BoardImage> existingImage = boardImageRepository.findByBoard_Id(boardId);
        // 이미 해당하는 post에 파일 정보를 삭제처리
        if(!existingImage.isEmpty()){
            existingImage.forEach(image -> {
                amazonS3.deleteObject(new DeleteObjectRequest(bucket, image.getSaveImageName())); // S3에서 삭제처리
                boardImageRepository.delete(image);
            });
        }

        if (multipartFile != null) {
            List<BoardImage> boardImages = multipartFile.stream()
                    .map(image -> (BoardImage) saveImage(image, "board", null, board))
                    .collect(Collectors.toList());

            return boardImages;
        }
        return Collections.emptyList(); // null인 경우 빈 리스트 반환

    }

    @Transactional
    public void deleteProfileImage(Long userId, Long profileImageId) {
        ProfileImage profileImage = profileImageRepository.findByIdAndUserId(profileImageId, userId).orElseThrow(ProfileImageNotFoundException::new);
        if (profileImage.isKakaoImage()) {
            throw new CannotDeleteKakaoImageException();
        }
        if (profileImage.isPrimary()) {
            throw new CannotDeletePrimaryImageException();
        }
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, profileImage.getSaveImageName()));
        profileImageRepository.delete(profileImage);

    }

    public void deleteProfileImages(Long userId, List<Long> profileImagesIds) {
        List<ProfileImage> profileImages = profileImageRepository.findAllById(profileImagesIds);
        for (ProfileImage profileImage : profileImages) {
            if (profileImage.isKakaoImage()) {
                throw new CannotDeleteKakaoImageException();
            }
            if (profileImage.isPrimary()) {
                throw new CannotDeletePrimaryImageException();
            }
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, profileImage.getSaveImageName()));
            profileImageRepository.delete(profileImage);
        }
    }

    @Transactional
    public void setPrimaryProfileImage(Long userId, Long profileImageId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFountException::new);

        Optional<ProfileImage> prevPrimaryImage = user.getProfileImages().stream()
                .filter(ProfileImage::isPrimary)
                .findAny();

        prevPrimaryImage.ifPresent(image -> image.setPrimary(false));

        ProfileImage nextPrimaryImage = profileImageRepository.findByIdAndUserId(profileImageId, userId).orElseThrow(ProfileImageNotFoundException::new);

        if (!nextPrimaryImage.isPrimary()) {
            nextPrimaryImage.setPrimary(true);
        }
    }

    @Transactional // TODO : 여기엔 아마 트랜잭셔널 전파가 안 될거임. 프로필이나 보드가 이미지 저장하다가 롤백 시에 문제 있을듯. 변경 원하면 말하셈.
    public Image saveImage(MultipartFile multipartFile, String folderName, User user, Board board){

        //파일 타입과 사이즈 저장
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        log.info(multipartFile.getContentType());

        //파일 이름
        String originalFilename = multipartFile.getOriginalFilename();

        //파일 이름이 비어있으면 (assert 오류 반환)
        assert originalFilename != null;
        //확장자
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        //파일 이름이 겹치지 않게
        String uuid = UUID.randomUUID().toString();

        //s3의 각 폴더에 따로 넣어서 보관
        String s3name = folderName+"/"+uuid+"_"+originalFilename;

        try (InputStream inputStream = multipartFile.getInputStream()) {
//            amazonS3.putObject(new PutObjectRequest(bucket, s3name, inputStream, objectMetadata)
//                    .withCannedAcl(CannedAccessControlList.PublicRead)); // ACL 활성화 했을 경우 이거 써도 됨.
            amazonS3.putObject(new PutObjectRequest(bucket, s3name, inputStream, objectMetadata)); //IAM으로 통신할 경우

        } catch (IOException e) {
            //파일을 제대로 받아오지 못했을때
            //Todo 예외처리 custom 따로 만들기
            throw new RuntimeException(e);
        }

        if (folderName.equals("profile")) {
            //파일 보관 url
            String storeFileUrl = amazonS3.getUrl(bucket,s3name).toString().replaceAll("\\+", "+");
            ProfileImage profileImage = ProfileImage.builder()
                    .originalImageName(originalFilename)
                    .saveImageName(s3name)
                    .imageUrl(storeFileUrl)
                    .extension(ext)
                    .user(user)
                    .build();

            profileImageRepository.save(profileImage); // 카스케이드가 있어서 저장 하는건지 아닌지 헷갈림.

            return profileImage;
        }
        else {
            //파일 보관 url
            String storeFileUrl = amazonS3.getUrl(bucket,s3name).toString().replaceAll("\\+", "+");
            BoardImage boardImage = BoardImage.builder()
                    .originalImageName(originalFilename)
                    .saveImageName(s3name)
                    .imageUrl(storeFileUrl)
                    .extension(ext)
                    .board(board)
                    .build();

            boardImageRepository.save(boardImage); // 카스케이드가 있어서 저장 하는건지 아닌지 헷갈림.

            return boardImage;
        }

    }

}
