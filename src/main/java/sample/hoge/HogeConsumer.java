package sample.hoge;

import akka.actor.ActorSystem;
import akka.kafka.ConsumerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class HogeConsumer {

  private final ActorSystem system;
  private final ActorMaterializer materializer;
  private final HogeService hogeService;

  public HogeConsumer(ActorSystem system, ActorMaterializer materializer, HogeService hogeService) {
    this.system = system;
    this.materializer = materializer;
    this.hogeService = hogeService;
  }

  public void start() {
    final ConsumerSettings<String, String> consumerSettings = ConsumerSettings
        .create(system, new StringDeserializer(), new StringDeserializer())
        .withBootstrapServers("localhost:9093")
        .withGroupId("pekko-huga-consumer")
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    Consumer.plainSource(consumerSettings, Subscriptions.topics("pekko-huga-topic"))
        .map(ConsumerRecord::value)
        .runWith(Sink.foreach(hogeService::exec), materializer);
  }
}