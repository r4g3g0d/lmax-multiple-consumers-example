package ro.softvision.lmaxpoc.entity;

import java.util.UUID;

public class ResponseEvent {
    private UUID id;
    private String data;

    public ResponseEvent(UUID id, String data) {
        this.id = id;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
