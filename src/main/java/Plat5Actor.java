import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.Date;

public class Plat5Actor extends AbstractActor {
    TrainSchedule trainSchedule = TrainSchedule.getInstance();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(
                TrainMessage.class,
                trainMessage -> printSensorMessage(trainMessage)).build();
    }

    private void printSensorMessage(TrainMessage trainMessage) {
        Train train = trainSchedule.getTrainById(trainMessage.getId());
        if (train != null) {
            String message = "___SENSOR MESSAGE START___\n\n";
            String fromto = train.isArriving() ? " arrived from station " + train.getFromCity() + " to platform 5" :
                    " departed to station " + train.getToCity() + " from platform 5";
            message += "Train " + train.getId();
            message += fromto + " with delay of " + train.getDelayInMinutesFromMessageTime(trainMessage.getTime()) +
                    " min at time "
                    + new Date(trainMessage.getTime())+"\n";
            message +="\n___SENSOR MESSAGE END___";
            System.out.println(message);
        }
    }

    public static Props props() {
        return Props.create(Plat5Actor.class);
    }
}
