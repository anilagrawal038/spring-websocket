package com.san.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.san.common.socket.WebSocketHandler;

@Configuration
@ComponentScan({ "com.san.common.controller", "com.san.common.socket" })

@EnableWebSocketMessageBroker // Required for AbstractWebSocketMessageBrokerConfigurer to work
@EnableWebSocket // Required for WebSocketConfigurer to work

// Reference : https://spring.io/guides/gs/messaging-stomp-websocket/
// Reference : http://www.concretepage.com/spring-4/spring-4-websocket-sockjs-stomp-tomcat-example


// Note : We can use AbstractWebSocketMessageBrokerConfigurer and WebSocketConfigurer separately as well
// If we need to provide only STOMP support we can use only AbstractWebSocketMessageBrokerConfigurer
// If we need to provide only simple web-socket we can use only WebSocketConfigurer

public class AppWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer implements WebSocketConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/calcApp");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/connectWebSocket"); // For Normal Communication
		registry.addEndpoint("/connectWebSocket").withSockJS(); // For STOMP Support
	}

	// To use Custom WebSocketHandler implements WebSocketConfigurer and define below function
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new WebSocketHandler(), "/test");
	}
}
