package goorm.honjaya.domain.user.service;

import goorm.honjaya.domain.user.dto.ProfileDto;
import goorm.honjaya.domain.user.entity.Profile;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.entity.UserStatus;
import goorm.honjaya.domain.user.exception.ProfileNotFoundException;
import goorm.honjaya.domain.user.exception.UnauthorizedAccessException;
import goorm.honjaya.domain.user.exception.UserNotFountException;
import goorm.honjaya.domain.user.repository.ProfileRepository;
import goorm.honjaya.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    @Transactional
    public void save(Long id, ProfileDto profileDto) {
        User user = userRepository.findById(id).orElseThrow(UserNotFountException::new);
        user.setStatus(UserStatus.ACTIVE);
        Profile profile = profileDto.toProfile();
        profile.setUser(user);
        profileRepository.save(profile);
    }

    public ProfileDto findByUserId(Long id) {
        Profile profile = profileRepository.findByUserId(id)
                .orElseThrow(ProfileNotFoundException::new);
        return ProfileDto.from(profile);
    }

    @Transactional
    public void update(Long userId, Long profileId, ProfileDto profileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFountException::new);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        if (!profile.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("요청하신 유저 아이디와 프로필 아이디가 불일치합니다.");
        }

        profile.updateFrom(profileDto);
    }
}
