package org.example.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.service.ScheduleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


@Getter
@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

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

        if (!login()) {
            return "Не удалось авторизоваться на сайте.";
        }

        String htmlSchedule = restTemplate.getForObject(getScheduleUrl(),String.class);

        if (htmlSchedule == null) {
            return "Не удалось получить расписание.";
        }

        return parseSchedule(htmlSchedule);
    }

    private String parseSchedule(String htmlSchedule) {
        Document document = Jsoup.parse(htmlSchedule);
        Element scheduleContainer = document.selectFirst("div.schedule.container");

        if (scheduleContainer == null) {
            return "Расписание не найдено.";
        }

        StringBuilder scheduleBuilder = new StringBuilder();

        // Обрабатываем блоки расписания
        for (Element item : scheduleContainer.select("div.schedule__item")) {
            // Заголовок (например, "Расписание экзаменов" или "Расписание занятий")
            Element title = item.selectFirst("h1");
            if (title != null) {
                scheduleBuilder.append(title.text()).append("\n");
            }

            // Время занятий/экзаменов
            Elements times = item.select("h5.card-title");
            for (Element time : times) {
                scheduleBuilder.append(time.text()).append("\n");
            }

            // Данные по каждому занятию/экзамену
            for (Element card : item.select("div.card")) {
                Element header = card.selectFirst("div.card-header h6");
                Element type = card.selectFirst("div.card-header span.alert");
                Element body = card.selectFirst("div.card-body");

                if (header != null) {
                    scheduleBuilder.append(header.text()).append(" - ");
                }
                if (type != null) {
                    scheduleBuilder.append(type.text()).append("\n");
                }

                if (body != null) {
                    for (Element bodyItem : body.children()) {
                        scheduleBuilder.append(bodyItem.text()).append("\n");
                    }
                }

                scheduleBuilder.append("\n");
            }

            scheduleBuilder.append("\n");
        }

        return scheduleBuilder.toString().trim();
    }


    private String fetchSchedule(String scheduleUrl, String group, String date) {

        String url = String.format("%s?group=%s&date=%s", scheduleUrl, group, date);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }

    private boolean login() {
        MultiValueMap <String, String> loginForm = new LinkedMultiValueMap<>();
        loginForm.add("username", getUserName());
        loginForm.add("password",getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(loginForm, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(getLoginURL(), request, String.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

}
