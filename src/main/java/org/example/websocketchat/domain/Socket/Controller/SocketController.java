package org.example.websocketchat.domain.Socket.Controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SocketController {
    private static final Logger LOGGER = LoggerFactory.getLogger( SocketController.class );

    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    // 새로운 사용자가 웹 소켓을 연결할 때 실행됨
    // @EventListener은 한개의 매개변수만 가질 수 있다.
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        LOGGER.info("Received a new web socket connection");
    }

    // 사용자가 웹 소켓 연결을 끊으면 실행됨
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();

        LOGGER.info("sessionId Disconnected : " + sessionId);
    }

    // /pub/cache 로 메시지를 발행한다.
    @MessageMapping("/cache")
    @SendTo("/sub/cache")
    public void sendMessage(Map<String, Object> params) {

        // /sub/cache 에 구독중인 client에 메세지를 보낸다.
        simpleMessageSendingOperations.convertAndSend("/sub/cache/" + params.get("channelId"), params);
    }

}
