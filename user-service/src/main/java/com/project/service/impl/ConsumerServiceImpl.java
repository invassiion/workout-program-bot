package com.project.service.impl;

import com.project.service.ConsumerService;
import com.project.service.MainService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final MainService mainService;

    public ConsumerServiceImpl(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    public void consumeTextMessageUpdates(Update update) {
        log.debug("User-service: text message is received");
        mainService.processTextMessage(update);
    }

}
