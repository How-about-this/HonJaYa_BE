package goorm.honjaya.domain.chat.service;

import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.chat.repository.ChatRoomRepository;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    private static final Logger logger = Logger.getLogger(MatchingService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Transactional
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public synchronized void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public synchronized Optional<User> matchUser(User user) {
        logger.info("Finding match for user: " + user.getUsername());

        user.setMatching(true);
        save(user);

        Optional<User> match = findMatch(user);
        if (match.isPresent()) {
            User matchedUser = match.get();
            createChatRoom(user, matchedUser);
            return Optional.of(matchedUser);
        }

        user.setMatching(false);
        save(user);
        return Optional.empty();
    }

    @Transactional
    public synchronized Optional<User> findMatch(User user) {
        List<User> potentialMatches = userRepository.findAll().stream()
                .filter(u -> !u.isMatched() && u.isMatching() && !u.getId().equals(user.getId()))
                .collect(Collectors.toList());

        return potentialMatches.stream()
                .map(u -> new MatchCandidate(u, calculateMatchScore(user, u)))
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .map(MatchCandidate::getUser)
                .findFirst();
    }

    private int getAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    @Transactional
    public void createChatRoom(User user1, User user2) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.addParticipant(user1);
        chatRoom.addParticipant(user2);
        chatRoomRepository.save(chatRoom);

        user1.setMatched(true);
        user2.setMatched(true);
        user1.setMatching(false);
        user2.setMatching(false);
        save(user1);
        save(user2);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private double calculateMatchScore(User user1, User user2) {
        double score = 0;

        if (user1.getProfile().getBirthday() != null && user2.getProfile().getBirthday() != null) {
            int ageDifference = Math.abs(getAge(user1.getProfile().getBirthday()) - getAge(user2.getProfile().getBirthday()));
            if (ageDifference <= 10) {
                score += (10 - ageDifference) * 10;
            }
        }

        if (user1.getProfile().getAddress().equals(user2.getProfile().getAddress())) {
            score += 25;
        }

        if (user1.getProfile().getMbti().equals(user2.getIdeal().getMbti())) {
            score += 25;
        }

        if (user1.getProfile().getReligion().equals(user2.getIdeal().getReligion())) {
            score += 25;
        }

        if (user1.getProfile().getHeight() >= user2.getIdeal().getMinHeight() && user1.getProfile().getHeight() <= user2.getIdeal().getMaxHeight()) {
            score += 25;
        }

        if (user1.getProfile().getWeight() >= user2.getIdeal().getMinWeight() && user1.getProfile().getWeight() <= user2.getIdeal().getMaxWeight()) {
            score += 25;
        }

        return score;
    }

    public boolean isMatching(Long userId) {
        User user = findUserById(userId);
        return user != null && user.isMatching();
    }

    private static class MatchCandidate {
        private final User user;
        private final double score;

        public MatchCandidate(User user, double score) {
            this.user = user;
            this.score = score;
        }

        public User getUser() {
            return user;
        }

        public double getScore() {
            return score;
        }
    }
}
