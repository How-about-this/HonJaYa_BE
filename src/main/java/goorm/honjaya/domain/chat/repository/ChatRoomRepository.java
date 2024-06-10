package goorm.honjaya.domain.chat.repository;

import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByParticipants(User user);
}
