public class DelayProducer {
    TrainSchedule trainSchedule = TrainSchedule.getInstance();

    public DelayInfoMessage sendDelayInfoMessage() {
        DelayInfoMessage delayInfoMessage = new DelayInfoMessage("-1",0);
        if (trainSchedule.getTrainsWaiting().isEmpty()) {
            System.out.println("nowhere to put delay...");

        } else {

            Train train = trainSchedule.getTrainsWaiting().get((int) (Math.random() * trainSchedule.getTrainsWaiting().size()));
            delayInfoMessage = new DelayInfoMessage(train.getId(), 5 * (int) (Math.random() * 4));
        }
        return delayInfoMessage;
    }

}
