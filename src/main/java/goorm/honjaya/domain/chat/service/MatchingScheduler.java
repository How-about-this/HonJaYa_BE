package goorm.honjaya.domain.chat.service;

import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.chat.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchingScheduler {

    @Autowired
    private MatchingService matchingService;

    @Scheduled(fixedRate = 60000) // 60초마다 실행
    public void matchUsers() {
        List<User> users = matchingService.getAllUsers();
        for (User user : users) {
            if (user.isMatching()) {
                try {
                    matchingService.matchUser(user.getId());
                } catch (Exception e) {
                    e.printStackTrace(); // 예외 처리 로직 추가
                }
            }
        }
    }
}
