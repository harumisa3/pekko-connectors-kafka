package sample.kafka;

import akka.actor.ActorSystem;
import akka.kafka.ConsumerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public abstract class AbstractKafkaConsumer {

    private final ActorSystem system;
    private final ActorMaterializer materializer;
    private final String groupId;
    private final String topic;

    public AbstractKafkaConsumer(ActorSystem system, ActorMaterializer materializer, String groupId, String topic) {
        this.system = system;
        this.materializer = materializer;
        this.groupId = groupId;
        this.topic = topic;
    }

    protected abstract void processMessage(String message);

    public void start() {
        final ConsumerSettings<String, String> consumerSettings = ConsumerSettings
            .create(system, new StringDeserializer(), new StringDeserializer())
            .withBootstrapServers("localhost:9093")
            .withGroupId(groupId)
            .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Consumer.plainSource(consumerSettings, Subscriptions.topics(topic))
            .map(ConsumerRecord::value)
            .runWith(Sink.foreach(this::processMessage), materializer);
    }
}
