package cc.elefteria.avro_test_producer.producer;

import cc.elefteria.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersProducer {
  private final KafkaTemplate<String, User> kafkaTemplate;
  
  public void send(User user) {
    kafkaTemplate.send("users-source-topic", user);
  }
}
