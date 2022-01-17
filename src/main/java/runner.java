import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Date;

public class runner {
    TrainSchedule trainSchedule = TrainSchedule.getInstance();

    public static void main(String[] args) {
        ActorSystem akkaSystem = ActorSystem.create("system");
        //ACTORS
        ActorRef plat1Actor = akkaSystem.actorOf(Plat1Actor.props());
        ActorRef plat2Actor = akkaSystem.actorOf(Plat2Actor.props());
        ActorRef plat3Actor = akkaSystem.actorOf(Plat3Actor.props());
        ActorRef plat4Actor = akkaSystem.actorOf(Plat4Actor.props());
        ActorRef plat5Actor = akkaSystem.actorOf(Plat5Actor.props());
        ActorRef mainBoardActor = akkaSystem.actorOf(MainBoardActor.props());

        TrainProducer trainProducer = new TrainProducer();
        DelayProducer delayProducer = new DelayProducer();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    //trainSchedule.refresh();
                    TrainMessage trainMessage = trainProducer.sendTrain();
                    DelayInfoMessage delayInfoMessage = delayProducer.sendDelayInfoMessage();
                    int plat = trainMessage.getPlatform();
                    switch (plat) {
                        case 1:
                            plat1Actor.tell(trainMessage, ActorRef.noSender());
                            break;
                        case 2:
                            plat2Actor.tell(trainMessage, ActorRef.noSender());
                            break;
                        case 3:
                            plat3Actor.tell(trainMessage, ActorRef.noSender());
                            break;
                        case 4:
                            plat4Actor.tell(trainMessage, ActorRef.noSender());
                            break;
                        case 5:
                            plat5Actor.tell(trainMessage, ActorRef.noSender());
                            break;
                        default:
                            System.out.println("platform : " + plat);
                    }

                    mainBoardActor.tell(trainMessage, ActorRef.noSender());
                    mainBoardActor.tell(delayInfoMessage, ActorRef.noSender());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
