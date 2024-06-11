package goorm.honjaya.domain.user.service;

import goorm.honjaya.domain.user.dto.ProfileDto;
import goorm.honjaya.domain.user.entity.Profile;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.entity.UserStatus;
import goorm.honjaya.domain.user.exception.ProfileNotFoundException;
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
    public void update(Long id, ProfileDto profileDto) {
        Profile profile = profileRepository.findByUserId(id)
                .orElseThrow(ProfileNotFoundException::new);

        profile.updateFrom(profileDto);
    }
}
