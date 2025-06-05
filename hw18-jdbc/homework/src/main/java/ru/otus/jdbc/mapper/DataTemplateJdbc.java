package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {
    private static final Logger log = LoggerFactory.getLogger(DataTemplateJdbc.class);

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    private T parse(ResultSet rs) {
        T object;
        try {
            object = entityClassMetaData.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can't create new instance", e);
        }

        var fields = entityClassMetaData.getAllFields();
        for (var field : fields) {
            try {
                field.setAccessible(true);
                field.set(object, rs.getObject(field.getName()));
            } catch (IllegalAccessException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return object;
    }

    private List<Object> getParamsFromFields(T object, List<Field> fields) {
        return fields.stream()
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
                    try {
                        rs.next();
                        return Optional.of(parse(rs));
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(), rs -> {
                    var list = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            list.add(parse(rs));
                        }
                        return list;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(
                    connection,
                    entitySQLMetaData.getInsertSql(),
                    getParamsFromFields(client, entityClassMetaData.getFieldsWithoutId()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            var fields = entityClassMetaData.getFieldsWithoutId();
            fields.add(entityClassMetaData.getIdField());

            dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getUpdateSql(), getParamsFromFields(client, fields));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
