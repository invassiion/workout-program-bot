package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.service.ConsumerService;
import org.example.service.MainService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.model.RabbitQueue.*;

@RequiredArgsConstructor
@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdate(Update update) {
        log.debug("NODE: Text message is received");
        mainService.processTextMessage(update);
    }

//    @Override
//    @RabbitListener(queues = MESSAGE_TO_PARSING)
//    public void consumeMessageToParsing(SendMessage sendMessage) {
//        log.debug("NODE: Message to parsing is received");
//    }

    @Override
    @RabbitListener(queues = BEFORE_PARSING)
    public void consumeBeforeParsing(SendMessage sendMessage) {
        log.debug("NODE: Message before parsing is received");
        mainService.processScheduleMessage(sendMessage);
    }

}
