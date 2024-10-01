package com.project.service;

import com.project.dto.UserDto;
import com.project.entity.User;
import com.project.model.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setChatId(userDto.getChatId());
        userRepository.save(user);
    }
}
