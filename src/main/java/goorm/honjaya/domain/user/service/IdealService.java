package goorm.honjaya.domain.user.service;

import goorm.honjaya.domain.user.dto.IdealDto;
import goorm.honjaya.domain.user.entity.Ideal;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.exception.ProfileNotFoundException;
import goorm.honjaya.domain.user.exception.UnauthorizedAccessException;
import goorm.honjaya.domain.user.exception.UserNotFountException;
import goorm.honjaya.domain.user.repository.IdealRepository;
import goorm.honjaya.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IdealService {

    private final IdealRepository idealRepository;

    private final UserRepository userRepository;

    @Transactional
    public void save(Long id, IdealDto idealDto) {
        User user = userRepository.findById(id).orElseThrow(UserNotFountException::new);
        Ideal ideal = idealDto.toIdeal();
        ideal.setUser(user);
        idealRepository.save(ideal);
    }

    public IdealDto findByUserId(Long id) {
        Ideal ideal = idealRepository.findByUserId(id)
                .orElseThrow(ProfileNotFoundException::new);
        return IdealDto.from(ideal);
    }

    @Transactional
    public void update(Long userId, Long idealId, IdealDto idealDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFountException::new);

        Ideal ideal = idealRepository.findById(idealId)
                .orElseThrow(ProfileNotFoundException::new);

        if (!ideal.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("요청하신 유저 아이디와 프로필 아이디가 불일치합니다.");
        }

        ideal.updateFrom(idealDto);
    }
}
