package com.project.service.impl;

import com.project.config.RabbitMQConfig;
import com.project.config.RabbitQueue;
import com.project.service.ConsumerService;
import com.project.service.MainService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;

    private RabbitQueue rabbitQueue;

    private final String textqueue  = rabbitQueue.getTextQueue();

    public ConsumerServiceImpl(MainService mainService, RabbitMQConfig rabbitMQConfig, RabbitQueue rabbitQueue) {
        this.mainService = mainService;
        this.rabbitQueue = rabbitQueue;
    }


    @Override
    @RabbitListener(queues = "queue" )
    public void consumeTextMessageUpdates(Update update) {
        log.debug("User-service: text message is received");
    }

    @Override
    public void consumeCommandMessageUpdates(Update update) {

    }
}
