package org.example.websocketchat.domain.Room.Entity.dto;

import lombok.Builder;
import lombok.Getter;
// 채팅방 DTO

@Getter
public class ChatRoomDTO {

    private String roomId;
    private String name;

    @Builder
    public ChatRoomDTO(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

}
