package goorm.honjaya.domain.chat.controller;

import goorm.honjaya.domain.chat.service.ChatService;
import goorm.honjaya.domain.chat.service.MatchingService;
import goorm.honjaya.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/match")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private ChatService chatService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> findMatch(@PathVariable Long userId) {
        try {
            User user = matchingService.findUserById(userId);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            user.setMatching(true);
            matchingService.save(user);
            return matchingService.findMatch(user)
                    .map(matchedUser -> {
                        user.setMatched(true);
                        matchedUser.setMatched(true);
                        user.setMatching(false);
                        matchedUser.setMatching(false);
                        matchingService.save(user);
                        matchingService.save(matchedUser);
                        chatService.createChatRoom(user, matchedUser);
                        return ResponseEntity.ok(matchedUser);
                    })
                    .orElse(ResponseEntity.noContent().build());
        } catch (Exception e) {
            e.printStackTrace(); // 디버깅### 매칭 시스템 전체 코드 업데이트
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
