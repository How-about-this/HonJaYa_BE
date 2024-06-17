package goorm.honjaya.domain.chat.controller;

import goorm.honjaya.domain.chat.ChatMessage;
import goorm.honjaya.domain.chat.dto.ChatRoomDTO;
import goorm.honjaya.domain.chat.service.ChatService;
import goorm.honjaya.domain.chat.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MatchingService matchingService;

    // 채팅 메시지 처리
    @MessageMapping("/chat.send/{roomId}")
    public void sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String roomId) {
        // 메시지 저장
        chatService.saveChatMessage(roomId, chatMessage);
        // 메시지 전송
        messagingTemplate.convertAndSend("/topic/chat/" + roomId, chatMessage);
    }

    // 사용자가 채팅룸에 입장 시 처리
    @MessageMapping("/chat.addUser/{roomId}")
    public void addUser(@Payload ChatMessage chatMessage, @DestinationVariable String roomId) {
        chatMessage.setType(ChatMessage.MessageType.JOIN);
        chatService.saveChatMessage(roomId, chatMessage);
        messagingTemplate.convertAndSend("/topic/chat/" + roomId, chatMessage);
    }

    // 채팅방 정보 조회
    @GetMapping("/rooms/{userId}")
    public List<ChatRoomDTO> getUserChatRooms(@PathVariable Long userId) {
        return matchingService.findChatRoomsByUserId(userId);
    }

    // 채팅방 삭제
    @DeleteMapping("/room/{roomId}") // 추가된 부분
    public void deleteChatRoom(@PathVariable Long roomId) {
        // 채팅 메시지 삭제
        chatService.deleteChatMessages(roomId.toString());
        // 채팅방 삭제
        chatService.deleteChatRoom(roomId);
    }

    // 채팅방에 있는 유저 정보 조회
    @GetMapping("/room/{roomId}/users") // 추가된 부분
    public List<ChatRoomDTO.ParticipantDto> getChatRoomUsers(@PathVariable Long roomId) {
        return matchingService.findChatRoomUsersByRoomId(roomId);
    }
}
