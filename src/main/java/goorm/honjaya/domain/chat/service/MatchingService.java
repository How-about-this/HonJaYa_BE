package goorm.honjaya.domain.chat.service;

import goorm.honjaya.domain.chat.dto.ChatRoomDTO;
import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.chat.repository.ChatRoomRepository;
import goorm.honjaya.domain.user.dto.UserDto;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public synchronized UserDto matchUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty() || optionalUser.get().isMatched()) {
            return null; // 유저를 찾을 수 없거나 이미 매칭된 경우 null 반환
        }

        User user = optionalUser.get();
        user.setMatching(true);
        userRepository.save(user);

        List<User> potentialMatches = userRepository.findAll().stream()
                .filter(u -> u.isMatching() && !u.equals(user) && !u.isMatched())
                .collect(Collectors.toList());

        User bestMatch = null;
        int highestScore = -1;

        for (User potentialMatch : potentialMatches) {
            if (!potentialMatch.getProfile().getGender().equals(user.getProfile().getGender())) {
                int score = calculateMatchScore(user, potentialMatch);
                if (score > highestScore) {
                    highestScore = score;
                    bestMatch = potentialMatch;
                }
            }
        }

        if (bestMatch != null) {
            createChatRoom(user, bestMatch);
            user.setMatched(true);
            user.setMatchedUser(bestMatch);
            bestMatch.setMatched(true);
            bestMatch.setMatchedUser(user);
            user.setMatching(false);
            bestMatch.setMatching(false);
            userRepository.save(user);
            userRepository.save(bestMatch);

            // 매칭된 사용자에게 알림 전송
            UserDto bestMatchDto = UserDto.from(bestMatch);
            UserDto userDto = UserDto.from(user);

            messagingTemplate.convertAndSend("/topic/match/" + user.getId(), bestMatchDto);
            messagingTemplate.convertAndSend("/topic/match/" + bestMatch.getId(), userDto);

            return userDto; // 매칭된 사용자 정보를 반환
        }
        return null;
    }

    public List<ChatRoomDTO> findChatRoomsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<ChatRoom> chatRooms = chatRoomRepository.findByParticipants(userOptional.get());
            return chatRooms.stream().map(ChatRoomDTO::from).collect(Collectors.toList());
        }
        return null;
    }

    private int calculateMatchScore(User user, User potentialMatch) {
        int score = 0;
        // 매칭 점수 계산 로직 추가
        return score;
    }

    private void createChatRoom(User user1, User user2) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.addParticipant(user1);
        chatRoom.addParticipant(user2);
        chatRoomRepository.save(chatRoom);
    }
}
