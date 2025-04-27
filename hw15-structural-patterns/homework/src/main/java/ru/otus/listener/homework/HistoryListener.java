package ru.otus.listener.homework;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> rep = new ConcurrentHashMap<>();

    @Override
    public void onUpdated(Message msg) {
        var msgCopy = msg.toBuilder().build();
        rep.put(msgCopy.getId(), msgCopy);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(rep.get(id));
    }
}
