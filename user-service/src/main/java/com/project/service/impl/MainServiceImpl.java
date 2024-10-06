package com.project.service.impl;

import com.project.entity.RawData;
import com.project.model.RawDataDAO;
import com.project.service.MainService;
import com.project.service.ProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Service
public class MainServiceImpl implements MainService {


    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;

    public MainServiceImpl(RawDataDAO userRepository, ProducerService producerService) {
        this.rawDataDAO = userRepository;
        this.producerService = producerService;
    }

    @Override
    public void processTextMessage(Update update) {
        saveUserData(update);

        var message = update.getMessage();
        var sendMesage = new SendMessage();
        sendMesage.setChatId(message.getChatId().toString());
        sendMesage.setText("Hello from UserService");
        producerService.producerAnswer(sendMesage);
    }

    private void saveUserData(Update update) {
        RawData user = RawData.builder().build();
        rawDataDAO.save(user);
    }
}
