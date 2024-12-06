package org.example.websocketchat.domain.Chat.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocketchat.domain.Chat.Entity.dto.ChatDTO;
import org.example.websocketchat.domain.Room.Entity.dto.ChatRoomDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private Map<String, ChatRoomDTO> chatRooms;
    private final SimpMessagingTemplate template;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoomDTO> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomDTO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoomDTO createRoom(String name) {
        String roomId = UUID.randomUUID().toString();
        ChatRoomDTO chatRoom = ChatRoomDTO.builder()
                .roomId(roomId)
                .name(name)
                .build();
        chatRooms.put(roomId, chatRoom);
        return chatRoom;
    }

    // /sub을 Config에서 설정해주었다.
    // 그래서 Message Broker가 해당 send를 캐치하고 해당 토픽을 구독하는 모든 사람에게 메시지를 보내게 된다.
    public void sendMessage(ChatDTO message) {
        // 메시지 저장로직 추가
        ChatRoomDTO chatRoom = chatRooms.get(message.getRoomId());

        // ex) roomId가 2일때, /sub/chat/room/2를 구독하는 유저들에게 모두 메시지가 보낸다.
        template.convertAndSend("/sub/chat/room/" + chatRoom.getRoomId(), message);
    }
}
