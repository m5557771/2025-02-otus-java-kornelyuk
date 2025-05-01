package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String fileName;

    static {
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    }

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        try {
            // Сериализуем Map в JSON и записываем в файл
            objectMapper.writeValue(new File(fileName), data);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
