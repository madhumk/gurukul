package com.gurukul.hub.service;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HubService {

    private static final Logger logger =
            LoggerFactory.getLogger(HubService.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    String TOPIC_NAME = "message_topic";
    public Message postMessage(Message message) {

        System.out.println(message.toString());
        logger.info(String.format("Message sent -> %s", message));
        String jsonString = new Gson().toJson(message);
        this.kafkaTemplate.send(TOPIC_NAME, jsonString);
        return message;
    }
}
