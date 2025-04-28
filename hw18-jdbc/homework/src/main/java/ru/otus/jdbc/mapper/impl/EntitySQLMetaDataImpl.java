package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s;", entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "select * from %s where %s = ?;",
                entityClassMetaData.getName(), entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        var fields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
        var questionMarks = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> "?")
                .collect(Collectors.joining(","));
        return String.format("insert into %s (%s) values (%s);", entityClassMetaData.getName(), fields, questionMarks);
    }

    @Override
    public String getUpdateSql() {
        var fields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> String.format("%s = ?", field.getName()))
                .collect(Collectors.joining(","));
        return String.format(
                "update %s set %s where %s = {?};",
                entityClassMetaData.getName(),
                fields,
                entityClassMetaData.getIdField().getName());
    }
}
