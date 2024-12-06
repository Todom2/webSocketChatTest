package org.example.websocketchat.domain.Chat.Entity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


// chatting 메세지를 담는 DTO
@Getter
@Setter
public class ChatDTO {

    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;

}
