package com.project.service.impl;

import com.project.entity.User;
import com.project.model.UserRepository;
import com.project.service.MainService;
import com.project.service.ProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Service
public class MainServiceImpl implements MainService {


    private final UserRepository userRepository;
    private final ProducerService producerService;

    public MainServiceImpl(UserRepository userRepository, ProducerService producerService) {
        this.userRepository = userRepository;
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
        User user = User.builder().build();
        userRepository.save(user);
    }
}
