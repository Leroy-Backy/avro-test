package cc.elefteria.avro_test_producer;

import cc.elefteria.avro_test_producer.producer.UsersProducer;
import cc.elefteria.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class ProducerController {
  
  private final UsersProducer usersProducer;

  private List<String> names = Arrays.asList(
      "Olivia", "Liam", "Emma", "Noah", "Ava",
      "Sophia", "Jackson", "Mia", "Amelia", "Lucas",
      "Ethan", "Harper", "Mason", "Ella", "Aiden",
      "James", "Evelyn", "Benjamin", "Scarlett", "Alexander",
      "Sofia", "Henry", "Avery", "Sebastian", "Aria",
      "Jack", "Luna", "Levi", "Isabella", "Mateo"
  );
  
  @GetMapping()
  public ResponseEntity<List<String>> getUsers(@RequestParam(required = false, defaultValue = "10") int amount) {
    List<User> res = IntStream.range(0, amount).mapToObj(o ->
        new User(
            names.get(ThreadLocalRandom.current().nextInt(names.size())),
            ThreadLocalRandom.current().nextInt(100)
        )
    ).toList();
    
    res.forEach(usersProducer::send);
    
    return ResponseEntity.ok(res.stream().map(user -> user.getName() + ": " + user.getAge()).toList());
  }
}
