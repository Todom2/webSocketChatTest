package org.example.websocketchat.domain.Chat.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocketchat.domain.Chat.Entity.dto.ChatDTO;
import org.example.websocketchat.domain.Chat.Service.ChatService;
import org.example.websocketchat.domain.Room.Entity.dto.ChatRoomDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class StompChatController {

    private final ChatService chatService;

    // WebSocketConfig에서 setApplicationDestinationPrefixes()를 통해 prefix를 /pub으로 설정 해주었기 때문에,
    // 경로가 한번 더 수정되어 /pub/chat/message로 바뀐다.
    @MessageMapping("/chat/message")
    public void sendMessage(ChatDTO message) {
        log.info("type : {}, message : {} , roomId : {}",message.getType(),message.getMessage(), message.getRoomId());
        chatService.sendMessage(message);
    }

    @PostMapping("/rooms")
    public ChatRoomDTO createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping("/rooms")
    public List<ChatRoomDTO> findAllRoom() {
        return chatService.findAllRoom();
    }

}