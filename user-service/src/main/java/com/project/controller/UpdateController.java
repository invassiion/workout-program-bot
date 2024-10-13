package com.project.controller;

import com.project.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

/*
Update controller принимает Post - запро с телом, содержащим
объект Update.
Полученное обновление передается в MainService.
 */
@RestController
@RequestMapping("/updates")
public class UpdateController {

    private final MainService mainService;

    @Autowired
    public UpdateController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping
    public ResponseEntity<String> receiveUpdate(@RequestBody Update update) {
        if (update == null) {
            return ResponseEntity.badRequest().body("Update is null");
        }

        mainService.processTextMessage(update);
        return ResponseEntity.ok("Update processed successfully");
    }
}
