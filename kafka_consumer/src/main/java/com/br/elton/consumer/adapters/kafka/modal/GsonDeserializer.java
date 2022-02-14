package com.br.elton.consumer.adapters.kafka.modal;

import com.br.elton.consumer.domain.model.Message;
import com.br.elton.consumer.domain.model.MessageAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

public class GsonDeserializer implements Deserializer<Message> {

    public static final String TYPE_CONFIG = "com.br.elton.consumer.type_config";

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageAdapter()).create();
    
    @Override
    public Message deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String(bytes), Message.class);
    }

}
