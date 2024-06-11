package goorm.honjaya.domain.chat.controller;

import goorm.honjaya.domain.chat.dto.ChatRoomDTO;
import goorm.honjaya.domain.user.dto.UserDto;
import goorm.honjaya.domain.chat.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/{userId}")
    public UserDto matchUser(@PathVariable Long userId) {
        UserDto matchedUser = matchingService.matchUser(userId);
        if (matchedUser != null) {
            // 매칭된 사용자에게 알림 전송
            messagingTemplate.convertAndSend("/topic/match/" + userId, matchedUser);
            messagingTemplate.convertAndSend("/topic/match/" + matchedUser.getId(), UserDto.from(matchingService.findUserById(userId).get()));
        }
        return matchedUser;
    }

    @GetMapping("/matched/{userId}")
    public UserDto getMatchedUser(@PathVariable Long userId) {
        return UserDto.from(matchingService.findUserById(userId).orElse(null));
    }

    @GetMapping("/chatrooms/{userId}")
    public List<ChatRoomDTO> getUserChatRooms(@PathVariable Long userId) {
        return matchingService.findChatRoomsByUserId(userId);
    }
}
