package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.service.ConsumerService;
import org.example.service.ParserService;
import org.example.service.ProducerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.example.model.RabbitQueue.BEFORE_PARSING;
import static org.example.model.RabbitQueue.MESSAGE_TO_PARSING;


@RequiredArgsConstructor
@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ParserService parserService;
    private final ProducerService producerService;

    @Override
    @RabbitListener(queues = MESSAGE_TO_PARSING)
    public void consumeMessageToParsing(SendMessage sendMessage) {
        log.debug("PARSER: Message to parsing is received");

        String[] parts = sendMessage.getText().split(",");
        String group = parts[0];
        String date = parts[1];

        String schedule = parserService.parseSchedule(group, date);

        SendMessage scheduleMessage = new SendMessage();
        scheduleMessage.setChatId(sendMessage.getChatId());
        scheduleMessage.setText(schedule);

        producerService.produceBeforeParsing(scheduleMessage);
    }

//    @Override
//    @RabbitListener(queues = BEFORE_PARSING)
//    public void consumeBeforeParsing(SendMessage sendMessage) {
//        log.debug("PARSER: Message before parsing is received");
//    }
}
