package com.project.service;

import com.fasterxml.jackson.databind.JsonNode;
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
            JsonNode jsonNode = objectMapper.readTree(message);
            String username = jsonNode.get("message").asText();
            Long chatId = jsonNode.get("chatId").asLong();

            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setChatId(chatId);
            userService.registerUser(userDto);

            System.out.println("User registered: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
