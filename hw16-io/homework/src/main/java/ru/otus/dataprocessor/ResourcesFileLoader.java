package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try {
            return objectMapper.readValue(
                    ResourcesFileLoader.class.getClassLoader().getResource(fileName), new TypeReference<>() {});

        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
