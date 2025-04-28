package ru.otus.processor.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

@SuppressWarnings({"java:S112"})
public class ProcessorThrowExceptionOnEvenSecond implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(ProcessorThrowExceptionOnEvenSecond.class);

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowExceptionOnEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        var currentSec = dateTimeProvider.getDate().getSecond();
        logger.info("currentSec: {}", currentSec);
        if (currentSec % 2 == 0) {
            throw new RuntimeException(String.format("Second is even: %s", currentSec));
        }

        return message.toBuilder().build();
    }
}
