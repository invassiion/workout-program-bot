package org.example.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.service.ScheduleService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Log4j
@Getter
@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${sibsutis.loginUrl}")
    private String loginURL;
    @Value("${sibsutis.scheduleUrl}")
    private String scheduleUrl;

    @Value("${sibsutis.userName}")
    private String userName;
    @Value("${sibsutis.password}")
    private String password;


    @Override
    public String getSchedule(String group, String date) {

//        try {
//            if (login()) {
//                Document schedulePage = Jsoup.connect(getScheduleUrl()).get();
//                Element scheduleElement = schedulePage.selectFirst("div.schedule");
//                if (scheduleElement!=null) {
//                    return scheduleElement.text();
//                } else {
//                    return "Расписание не найдено.";
//                }
//            } else {
//                return "Ошибка входа на сайт.";
//            }
//        } catch (IOException e) {
//            log.error("Ошибка в получении расписания" + e);
//            return "Произошла ошибка при получении расписания.";
//        }
        return "Ошибка";

    }

    private String parseSchedule(String htmlSchedule) {
        StringBuilder scheduleBuilder = new StringBuilder();
        Document document = Jsoup.parse(htmlSchedule);
        Elements scheduleItems = document.select(".schedule__item");

        for (Element scheduleItem : scheduleItems) {
            Element timeTitle = scheduleItem.selectFirst("h5.card-title");
            if (timeTitle != null) {
                scheduleBuilder.append(timeTitle.text()).append("\n");
            }

            Elements lessonCards = scheduleItem.select(".card.border-info.my-3");
            for (Element lessonCard : lessonCards) {
                Element lessonTitle = lessonCard.selectFirst(".card-header.bold h6");
                if (lessonTitle != null) {
                    scheduleBuilder.append(lessonTitle.text()).append("\n");
                }
                Element lessonType = lessonCard.selectFirst(".card-header.bold .alert.alert-info");
                if (lessonType != null) {
                    scheduleBuilder.append(lessonType.text()).append("\n");
                }
                Elements lessonDetails = lessonCard.select(".card-body div");
                for (Element detail : lessonDetails) {
                    scheduleBuilder.append(detail.text()).append("\n");
                }
                scheduleBuilder.append("\n");
            }
            scheduleBuilder.append("\n");
        }
        return scheduleBuilder.toString();
    }

        private boolean login () throws IOException {
            Connection.Response loginFormResponse = Jsoup.connect(getLoginURL())
                    .method(Connection.Method.GET)
                    .execute();
            Document loginPage = loginFormResponse.parse();
            String authToken = loginPage.select("input[name=_csrf]").attr("value");

            Connection.Response loginActionResponse = Jsoup.connect(getLoginURL())
                    .cookies(loginFormResponse.cookies())
                    .data("username", getUserName())
                    .data("password", getPassword())
                    .data("_csrf", authToken)
                    .method(Connection.Method.POST)
                    .execute();

            return loginActionResponse.statusCode() == 200;
        }

    }

