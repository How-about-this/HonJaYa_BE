package goorm.honjaya.domain.chat.service;

import goorm.honjaya.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class MatchingScheduler {

    private static final Logger logger = Logger.getLogger(MatchingScheduler.class.getName());

    private final MatchingService matchingService;

    @Autowired
    public MatchingScheduler(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void scheduleMatch() {
        List<User> users = matchingService.getAllUsers().stream()
                .filter(user -> !user.isMatched() && matchingService.isMatching(user.getId()))
                .collect(Collectors.toList());

        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            Optional<User> match = matchingService.findMatch(user);
            if (match.isPresent()) {
                User matchedUser = match.get();
                user.setMatched(true);
                matchedUser.setMatched(true);
                user.setMatching(false);
                matchedUser.setMatching(false);
                matchingService.save(user);
                matchingService.save(matchedUser);
                matchingService.createChatRoom(user, matchedUser);
                iterator.remove();
                users.remove(matchedUser);
            }
        }
    }
}
