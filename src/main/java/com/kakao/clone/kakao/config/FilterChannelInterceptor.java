package com.kakao.clone.kakao.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class FilterChannelInterceptor implements ChannelInterceptor {

    //SPOMP 인증을 위한 메서드
    // 사용법은 잘 모르겠다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        System.out.println("full message:" + message);
        System.out.println("auth:" + headerAccessor.getNativeHeader("Authorization"));
        System.out.println(headerAccessor.getHeader("nativeHeaders").getClass());
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            System.out.println("msg: " + "conne");
        }
        //throw new MessagingException("no permission! ");
        return message;
    }
}
