package com.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.UserDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageListener {

    @Autowired
    private UserService userService;

    @RabbitListener(queues = "userQueue")
    public void handleUserRegistration(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            UserDto userDto = objectMapper.readValue(message, UserDto.class);
            userService.registerUser(userDto);
            System.out.println("User registered: " + userDto.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
