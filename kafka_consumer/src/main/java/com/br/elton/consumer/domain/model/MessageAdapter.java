package com.br.elton.consumer.domain.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MessageAdapter implements JsonSerializer<Message>, JsonDeserializer<Message> {

    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", message.getPayload().getClass().getName());
        obj.add("payload", context.serialize(message.getPayload()));
        obj.add("correlationId", context.serialize(message.getId()));

        return obj;
    }

    @Override
    public Message deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        var obj = json.getAsJsonObject();
        String payloadType =  obj.get("type").getAsString();
        var newPayloadType = payloadType.replaceAll("producer", "consumer");
        var correlationId = (CorrelationId) context.deserialize(obj.get("correlationId"), CorrelationId.class);

        try {
            var payload = context.deserialize(obj.get("payload"), Class.forName(newPayloadType));
            return new Message(correlationId, payload);
        } catch (ClassNotFoundException e) {
            throw  new JsonParseException(e);
        }
    }

}
