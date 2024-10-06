package com.project.service.impl;


import com.project.model.RawDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private RawDataDAO userRepository;

}
