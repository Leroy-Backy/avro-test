package cc.elefteria.test_avro_streams.streams;

import cc.elefteria.model.User;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStream {
  
  private final SpecificAvroSerde<User> userSpecificAvroSerde;
  
  @Bean
  public KStream<String, User> kStream(StreamsBuilder builder) {
    KStream<String, User> kStream = builder.stream("users-source-topic", Consumed.with(Serdes.String(), userSpecificAvroSerde));
    
    kStream
        .filter((key, user) -> user.getAge() >= 18)
        .to("users-adult-topic", Produced.with(Serdes.String(), userSpecificAvroSerde));
    
    return kStream;
  }
}
