package goorm.honjaya.domain.user.service;

import goorm.honjaya.domain.user.dto.UserDto;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.exception.UserNotFountException;
import goorm.honjaya.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFountException::new);
        return UserDto.from(user);
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFountException::new);
        return UserDto.from(user);
    }
}
