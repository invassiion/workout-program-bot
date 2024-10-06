package configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

public final class RabbitQueue {

    public static final String TEXT_QUEUE = "textMessageQueue";  // Добавляем как константу
    public static final String ANSWER_QUEUE = "answerMessageQueue";

}
