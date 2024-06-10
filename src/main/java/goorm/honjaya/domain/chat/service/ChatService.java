package goorm.honjaya.domain.chat.service;

import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.chat.repository.ChatRoomRepository;
import goorm.honjaya.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoom createChatRoom(User user1, User user2) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.addParticipant(user1);
        chatRoom.addParticipant(user2);
        return chatRoomRepository.save(chatRoom);
    }
}
