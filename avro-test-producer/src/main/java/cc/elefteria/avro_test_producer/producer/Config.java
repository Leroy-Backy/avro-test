package cc.elefteria.avro_test_producer.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Config {
  
  @Bean
  public NewTopic usersTopic() {
    return TopicBuilder.name("users-source-topic").build();
  }
}
