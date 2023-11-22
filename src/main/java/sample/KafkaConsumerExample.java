package sample;

import akka.actor.ActorSystem;
import akka.kafka.ConsumerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerExample {
  public static void main(String[] args) {
    final ActorSystem system = ActorSystem.create("pekko-consumer");
    final ActorMaterializer materializer = ActorMaterializer.create(system);

    final ConsumerSettings<String, String> consumerSettings = ConsumerSettings
        .create(system, new StringDeserializer(), new StringDeserializer())
        .withBootstrapServers("localhost:9093")
        .withGroupId("group-id")
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    Consumer.plainSource(consumerSettings, Subscriptions.topics("pekko-topic"))
        .map(ConsumerRecord::value)
        .runWith(Sink.foreach(System.out::println), materializer);
  }
}