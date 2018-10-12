package com.san.common.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.san.common.stomp.co.CalInput;
import com.san.common.stomp.dto.CalResult;

@Controller
public class WebSocketController {

	@MessageMapping("/add")
	@SendTo("/topic/showResult")
	public CalResult addNum(CalInput input) throws Exception {
		Thread.sleep(2000);
		CalResult result = new CalResult(input.getNum1() + "+" + input.getNum2() + "=" + (input.getNum1() + input.getNum2()));
		return result;
	}

	@RequestMapping("/start")
	public String start() {
		return "start";
	}
}
