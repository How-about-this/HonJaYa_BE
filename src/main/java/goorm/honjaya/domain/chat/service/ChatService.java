package goorm.honjaya.domain.chat.service;

import goorm.honjaya.domain.chat.ChatMessage;
import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RedisTemplate<String, Object> chatRedisTemplate;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅 메시지 저장
    public void saveChatMessage(String roomId, ChatMessage chatMessage) {
        String key = "chat_room:" + roomId;
        chatRedisTemplate.opsForList().rightPush(key, chatMessage);
    }

    // 채팅 메시지 불러오기
    public List<Object> getChatMessages(String roomId) {
        String key = "chat_room:" + roomId;
        return chatRedisTemplate.opsForList().range(key, 0, -1);
    }

    // 채팅 메시지 삭제
    public void deleteChatMessages(String roomId) {
        String key = "chat_room:" + roomId;
        chatRedisTemplate.delete(key);
    }

    // 채팅방 삭제
    public void deleteChatRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }
}
