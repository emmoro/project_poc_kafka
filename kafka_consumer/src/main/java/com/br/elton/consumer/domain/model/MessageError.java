package com.br.elton.consumer.domain.model;

public class MessageError<T> {

    private String messageError;

    private T object;

    public MessageError() {

    }

    public MessageError(String messageError, T object) {
        this.messageError = messageError;
        this.object = object;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "MessageError{" +
                "messageError='" + messageError + '\'' +
                ", object=" + object +
                '}';
    }

}
