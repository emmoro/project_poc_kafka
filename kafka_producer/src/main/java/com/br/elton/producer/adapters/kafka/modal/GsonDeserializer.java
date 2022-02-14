package com.br.elton.producer.adapters.kafka.modal;

import com.br.elton.producer.domain.model.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

public class GsonDeserializer implements Deserializer<Message> {

    public static final String TYPE_CONFIG = "com.br.elton.producer.type_config";
    private Gson gson = new GsonBuilder().create();

    @Override
    public Message deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String(bytes), Message.class);
    }

}
