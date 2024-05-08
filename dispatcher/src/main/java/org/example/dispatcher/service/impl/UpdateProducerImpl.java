package org.example.dispatcher.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.dispatcher.service.UpdateProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.model.RabbitQueue.TEXT_MESSAGE_UPDATE;

@RequiredArgsConstructor
@Log4j
@Service
public class UpdateProducerImpl implements UpdateProducer {

    @Autowired
    private final RabbitTemplate rabbitTemplate;


    @Override
    public void produce(Update update) {
        log.debug(update.getMessage().getText());
        rabbitTemplate.convertAndSend(TEXT_MESSAGE_UPDATE, update);
    }
}
