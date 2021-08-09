package com.gurukul.hub.service;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;

@Service
public class HubService {

    private static final Logger logger =
            LoggerFactory.getLogger(HubService.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    String TOPIC_NAME = "message_topic3";
    public Message postMessage(Message msg) {



        return sendMessage(msg);
    }

    public Message sendMessage(Message message) {
        long currentTime = System.currentTimeMillis();
        message.setAcceptedTime(new Date(currentTime));
        logger.info(String.format("Message sent -> %s", message));
        String jsonString = new Gson().toJson(message);
        ListenableFuture<SendResult<String, String>> future =
                this.kafkaTemplate.send(TOPIC_NAME, message.getSenderId(),jsonString);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });


    return  message;
    }
}
