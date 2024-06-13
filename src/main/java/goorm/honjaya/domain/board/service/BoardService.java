package goorm.honjaya.domain.board.service;

import goorm.honjaya.domain.board.dto.BoardDto;
import goorm.honjaya.domain.board.dto.BoardPageDto;
import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.board.entity.Category;
import goorm.honjaya.domain.board.repository.BoardRepository;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.repository.UserRepository;
import goorm.honjaya.global.auth.CustomOAuth2User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //id별 조회
    public BoardDto findBoardById(Long id) {
        return boardRepository.findById(id).map(BoardDto::toDto).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않음"));
    }

    //게시물 저장
    @Transactional
    public BoardDto save(BoardDto boardDto, CustomOAuth2User customOAuth2User) {
        String username = customOAuth2User.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("아이디 존재하지 않음"));

        Board board = Board.builder()
                .title(boardDto.getTitle())
                .user(user)
                .content(boardDto.getContent())
                .category(boardDto.getCategory())
                .build();

        board = boardRepository.save(board);
        return BoardDto.toDto(board);
    }

    //게시물 수정
    @Transactional
    public BoardDto update(Long id, BoardDto boardDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않음"));

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setCategory(boardDto.getCategory());
//        board = boardRepository.save(board);
        return BoardDto.toDto(board);
    }

    //게시물 삭제
    public void delete(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new IllegalArgumentException("게시물 없음");
        }
        boardRepository.deleteById(id);
        log.info("삭제 완료");
    }


    //페이징
    public Page<BoardPageDto> getBoardsWithPagination(Pageable pageable, String category) {
        if (category == null) {
            Page<Board> boards = boardRepository.findAll(pageable);
            return boards.map(BoardPageDto::from);
        } else {
            Category boardCategory = Objects.equals(category, "RECRUITMENT") ? Category.RECRUITMENT : Category.REVIEW;
            Page<Board> boards = boardRepository.findByCategory(pageable, boardCategory);
            return boards.map(BoardPageDto::from);
        }
    }
}
