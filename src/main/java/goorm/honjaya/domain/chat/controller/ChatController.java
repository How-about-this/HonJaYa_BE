package goorm.honjaya.domain.chat.controller;

import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.chat.service.ChatService;
import goorm.honjaya.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody User[] users) {
        if (users.length != 2) {
            return ResponseEntity.badRequest().build();
        }
        ChatRoom chatRoom = chatService.createChatRoom(users[0], users[1]);
        return ResponseEntity.ok(chatRoom);
    }
}

