package com.kakao.clone.kakao.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp")
                .setAllowedOriginPatterns("*");
//                .withSockJS()
//                .setWebSocketEnabled(false)
//                .setSessionCookieNeeded(false);
        System.out.println("stomp1");
    }

    /*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/
    // Client 에서 SEND 요청을 처리
    //Spring docs 에서는 /topic, /queue로 나오나 편의상 /pub, /sub로 변경
    //해당 경로로 SimpleBroker를 등록.
    // SimpleBroker는 해당하는 경로를 SUBSCRIBE하는 Client에게 메세지를 전달하는 간단한 작업을 수행
    //enableStompBrokerRelay
    //SimpleBroker의 기능과 외부 Message Broker( RabbitMQ, ActiveMQ 등 )에 메세지를 전달하는 기능을 가짐
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        System.out.println("stomp2");
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
