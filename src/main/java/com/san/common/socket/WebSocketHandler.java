//-----------------------------------------------------------------------------------------------------------
//					ORGANIZATION NAME
//Group							: Common - Project
//Product / Project				: ass-common
//Module						: ass-common
//Package Name					: com.san.common.socket
//File Name						: WebSocketHandler.java
//Author						: anil
//Contact						: anilagrawal038@gmail.com
//Date written (DD MMM YYYY)	: 9 Dec, 2016 10:04:16 PM
//Description					:  
//-----------------------------------------------------------------------------------------------------------
//					CHANGE HISTORY
//-----------------------------------------------------------------------------------------------------------
//Date			Change By		Modified Function 			Change Description (Bug No. (If Any))
//(DD MMM YYYY)
//9 Dec, 2016   	anil			N/A							File Created
//-----------------------------------------------------------------------------------------------------------
package com.san.common.socket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// Reference : https://www.devglan.com/spring-boot/spring-websocket-integration-example-without-stomp
// Implement WebSocket without using STOMP
@Component
public class WebSocketHandler extends TextWebSocketHandler {

	List<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
		// System.out.println("msg recieved");
		// Map value = new Gson().fromJson(message.getPayload(), Map.class);
		// String name = value.get("name");

		String name = message.getPayload();
		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(new TextMessage(name + " messaged"));
		}
		session.sendMessage(new TextMessage("Hello " + name + " !"));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// the messages will be broadcasted to all users.
		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(new TextMessage("Anonymous joined conversation"));
		}
		sessions.add(session);
		session.sendMessage(new TextMessage("Connection establised"));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(new TextMessage("Anonymous left conversation"));
		}
	}
}
