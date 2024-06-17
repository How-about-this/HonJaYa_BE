package goorm.honjaya.domain.chat.service;

import goorm.honjaya.domain.chat.ChatMessage;
import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.chat.repository.ChatRoomRepository;
import goorm.honjaya.domain.chat.repository.ChatMessageRepository; // 추가된 부분
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomScheduler {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository; // 추가된 부분
    private final ChatService chatService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void checkExpiredChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoom.getCreatedAt().plusHours(24).isBefore(LocalDateTime.now())) {
                // 채팅 메시지 데이터베이스에 저장
                List<Object> chatMessages = chatService.getChatMessages(chatRoom.getId().toString());
                for (Object message : chatMessages) {
                    chatMessageRepository.save((ChatMessage) message); // 추가된 부분
                }
                // Redis에서 채팅 메시지 삭제
                chatService.deleteChatMessages(chatRoom.getId().toString());
                // 채팅방 삭제
                chatService.deleteChatRoom(chatRoom.getId());
            }
        }
    }
}
