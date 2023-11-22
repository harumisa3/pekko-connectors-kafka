package sample.hoge;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import sample.kafka.AbstractKafkaConsumer;

public class HogeConsumer extends AbstractKafkaConsumer {

  private final HogeService hogeService;

  public HogeConsumer(
      ActorSystem system,
      ActorMaterializer materializer,
      HogeService hogeService) {
    super(
        system, materializer,
        "pekko-hoge-consumer",
        "pekko-hoge-topic");
    this.hogeService = hogeService;
  }

  @Override
  protected void processMessage(String message) {
    hogeService.exec(message);
  }
}
