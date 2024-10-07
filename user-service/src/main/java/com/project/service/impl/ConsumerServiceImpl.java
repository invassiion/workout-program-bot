package com.project.service.impl;

import com.project.config.RabbitMQConfig;
import com.project.service.ConsumerService;
import com.project.service.MainService;
import configuration.RabbitQueue;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static configuration.RabbitQueue.*;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final MainService mainService;

    public ConsumerServiceImpl(MainService mainService, RabbitMQConfig rabbitMQConfig) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = TEXT_QUEUE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("User-service: text message is received");
        mainService.processTextMessage(update);
    }

}
