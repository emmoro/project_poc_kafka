package com.br.elton.consumer.domain.model;

import java.util.UUID;

public class CorrelationId {

    private String id;

    public CorrelationId(String name) {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CorrelationId{" +
                "id='" + id + '\'' +
                '}';
    }

}
