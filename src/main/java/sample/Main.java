package sample;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import sample.hoge.HogeConsumer;
import sample.hoge.HogeService;
import sample.hoge.HogeServiceImpl;

public class Main {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("pekko-system");
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        HogeService hogeService = new HogeServiceImpl();
        HogeConsumer hogeConsumer = new HogeConsumer(system, materializer, hogeService);
        hogeConsumer.start();
    }
}
