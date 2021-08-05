package com.gurukul.hub.controller;

import com.gurukul.hub.service.HubService;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hub")
public class HubController {

    @Autowired
    private HubService hubService;

    @PostMapping("/")
    public Message postMessage(@RequestBody Message message) {

        return hubService.postMessage(message);
    }

}
