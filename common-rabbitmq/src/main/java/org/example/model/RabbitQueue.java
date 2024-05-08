package org.example.model;


public class RabbitQueue {
//TODO Исправить ошибку с получением названия очереди из application.properties
//    Could not resolve placeholder 'rabbitmq.queue.answer-message' in value "${rabbitmq.queue.answer-message}"

//    public static final String TEXT_MESSAGE_UPDATE = "${rabbitmq.queue.text-message-update}";
//    public static final String ANSWER_MESSAGE = "${rabbitmq.queue.answer-message}";

    public static final String TEXT_MESSAGE_UPDATE = "text_message_update";
    public static final String ANSWER_MESSAGE = "answer_message";
}
