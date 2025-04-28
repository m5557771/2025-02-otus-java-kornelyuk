package ru.otus.processor.homework;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

class ProcessorThrowExceptionOnEvenSecondTest {

    @Test
    void positiveOddSecond() {
        // given
        var processor = new ProcessorThrowExceptionOnEvenSecond(() -> {
            return LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC);
        });
        var message = new Message.Builder(1L).build();

        // when

        // then
        Assertions.assertDoesNotThrow(() -> {
            processor.process(message);
        });
    }

    @Test
    void negativeEvenSecond() {
        // given
        var processor = new ProcessorThrowExceptionOnEvenSecond(() -> {
            return LocalDateTime.ofEpochSecond(2L, 0, ZoneOffset.UTC);
        });
        var message = new Message.Builder(1L).build();

        // when

        // then
        Assertions.assertThrows(RuntimeException.class, () -> {
            processor.process(message);
        });
    }
}
