package com.project.controller;

import com.project.service.UpdateService;
import com.project.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Log4j
@RestController
@RequestMapping("/update")
public class UpdateController {

    private final UpdateService updateService;

    @Autowired
    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PostMapping
    public ResponseEntity<String> receiveUpdate(@RequestBody Update update) {
        log.info("Received update from user-service: " + update);
        updateService.processUpdate(update);

        return ResponseEntity.ok("Update processed succesfully");
    }



}
