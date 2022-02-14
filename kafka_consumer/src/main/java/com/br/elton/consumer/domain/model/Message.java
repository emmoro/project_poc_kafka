package com.br.elton.consumer.domain.model;

public class Message<T> {

    private CorrelationId id;

    private T payload;

    public Message(CorrelationId id, T payload) {
        this.id = id;
        this.payload = payload;
    }

    public CorrelationId getId() {
        return id;
    }

    public void setId(CorrelationId id) {
        this.id = id;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", payload=" + payload +
                '}';
    }

}
