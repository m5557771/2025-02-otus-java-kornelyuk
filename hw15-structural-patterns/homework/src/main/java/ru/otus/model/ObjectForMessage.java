package ru.otus.model;

import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public ObjectForMessage(ObjectForMessage obj) {
        if (obj != null) {
            this.data = obj.data.stream().toList();
        }
    }

    public ObjectForMessage() {}

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
