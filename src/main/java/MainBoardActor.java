import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.Date;
import java.util.List;

public class MainBoardActor extends AbstractActor {
    TrainSchedule trainSchedule = TrainSchedule.getInstance();  // POZOOOOOOOOOOOOR. synchronized

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(
                        TrainMessage.class,
                        trainMessage -> handleTrainMessage(trainMessage)).
                match(DelayInfoMessage.class, delayInfoMessage -> handlePlannedDelay(delayInfoMessage)).build();
    }

    private void handlePlannedDelay(DelayInfoMessage delayInfoMessage) {
        if (delayInfoMessage != null) {
            String id = delayInfoMessage.getId();
            Train train = trainSchedule.getTrainById(id);
            if (train != null) {


                train.updateAfterPlannedDelay(delayInfoMessage.getDelay());
                System.out.println("__DELAY MESSAGE__\nTrain " + train.getId() +
                        " will have delay of " + delayInfoMessage.delay +
                        " minutes. The delay may change over time. We are sorry for the inconvenience.\n__DELAY MESSAGE__");
            }
        }
    }

    public void handleTrainMessage(TrainMessage trainMessage) {
        if (!trainMessage.getId().equals("-1")) {
            Train train = trainSchedule.getTrainById(trainMessage.getId());

            trainSchedule.getDepartures().remove(train);
        }
        trainSchedule.refresh();
        refreshMainBoard(trainMessage.getTime());
    }


    public void refreshMainBoard(long time) {
        List<Train> departures = trainSchedule.getDepartures();
        int trainCount = departures.size() > Constants.TRAIN_COUNT ? Constants
                .TRAIN_COUNT : departures.size();

        String mainBoardData = "\n_____________________________________________________________________________________________________\nACTUAL TIME : " +
                new Date() + "\n_____________________________________________________________________________________________________\n" +
                Constants.printWithSpacing("Train", Constants.SPACING) +
                Constants.printWithSpacing("Destination", Constants.SPACING) +
                Constants.printWithSpacing("Departure Time", Constants.BIG_SPACING) +
                Constants.printWithSpacing("Platform", Constants.SPACING) +
                "Delay\n";


        for (int i = 0; i < trainCount; i++) {
            Train train = departures.get(i);
            mainBoardData += Constants.printWithSpacing(train.getId(), Constants.SPACING) +
                    Constants.printWithSpacing(train.getToCity(), Constants.SPACING) +
                    Constants.printWithSpacing(train.getPlannedArrivalDepartureTime().toString(), Constants.BIG_SPACING)
                    + Constants.printWithSpacing("" + train.getPlatform(), Constants.SPACING) +
                    train.getDelay() + "\n";

        }
        mainBoardData += "_____________________________________________________________________________________________________";
        System.out.println(mainBoardData);

    }

    public static Props props() {
        return Props.create(MainBoardActor.class);
    }
}
