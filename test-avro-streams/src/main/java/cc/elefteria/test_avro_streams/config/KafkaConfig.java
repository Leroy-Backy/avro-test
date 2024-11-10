package cc.elefteria.test_avro_streams.config;

import cc.elefteria.model.User;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;
  @Value("${spring.kafka.properties.schema.registry.url}")
  private String registryUrl;
  
  @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
  public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
    Map<String, Object> config = new HashMap<>();
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-avro-streams");
    config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    return new KafkaStreamsConfiguration(config);
  }

  @Bean
  public <T extends SpecificRecord> SpecificAvroSerde<T> getSpecificAvroSerde() {
    SpecificAvroSerde<T> avroSerde = new SpecificAvroSerde<>();
    Map<String, String> serdeConfig = Collections.singletonMap(
        "schema.registry.url", registryUrl
    );
    avroSerde.configure(serdeConfig, false);
    return avroSerde;
  }

  @Bean
  public NewTopic usersTopic() {
    return TopicBuilder.name("users-source-topic").build();
  }

  @Bean
  public NewTopic usersAdultTopic() {
    return TopicBuilder.name("users-adult-topic").build();
  }
}
