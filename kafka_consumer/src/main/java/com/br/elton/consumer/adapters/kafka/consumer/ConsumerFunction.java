package com.br.elton.consumer.adapters.kafka.consumer;

import com.br.elton.consumer.domain.model.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {

    public void consume(ConsumerRecord<String, Message<T>> record) throws Exception;

}
