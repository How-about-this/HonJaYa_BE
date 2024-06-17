package goorm.honjaya.domain.chat.repository;

import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.chat.entity.ChatRoomUser;
import goorm.honjaya.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    List<ChatRoomUser> findByUser(User user);
    List<ChatRoomUser> findByChatRoom(ChatRoom chatRoom);
}
