package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import ru.otus.annotation.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getDeclaredConstructor();
        } catch (Exception e) {
            throw new RuntimeException("Can't find constructor", e);
        }
    }

    @Override
    public Field getIdField() {
        for (var field : getAllFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }

        throw new NoSuchElementException("Use @Id annotation to mark id field");
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        var fields = getAllFields();

        return fields.stream()
                .filter(field -> !field.getName().equals(getIdField().getName()))
                .toList();
    }
}
