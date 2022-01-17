
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TrainProducer {

    TrainSchedule trainSchedule = TrainSchedule.getInstance();
    BufferedWriter bw;

    public TrainMessage sendTrain() {
        trainSchedule.refresh();
        TrainMessage trainMessage = new TrainMessage("-1", -1, new Date().getTime());
        if (trainSchedule.getTrainsToBeSent().isEmpty()) {
            System.out.println("Ops...neni co posielat");

        } else {
            Train train = null;
            long now = new Date().getTime();
            boolean isThereTrainToBeSent = false;
            for (int i = 0; i < trainSchedule.getTrainsToBeSent().size(); i++) {
                Train tempTrain = trainSchedule.getTrainsToBeSent().get(i);
                if (tempTrain.shouldBeGone()) {
                    train = tempTrain;
                    isThereTrainToBeSent = true;
                    break;
                }
            }
            if (isThereTrainToBeSent) {
                trainSchedule.getTrainsToBeSent().remove(train);
                trainMessage = new TrainMessage(
                        train.getId(),
                        train.getPlatform(),
                        new Date().getTime());

                try {
                    bw = new BufferedWriter(new FileWriter("trains_sent.txt"));
                    bw.write(trainSchedule.updateTrainsSentTxt(train.getId()));
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return trainMessage;
    }
}
