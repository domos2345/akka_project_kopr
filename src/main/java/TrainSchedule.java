import org.apache.commons.lang3.time.DateUtils;

import java.util.*;

public class TrainSchedule {


    private static TrainSchedule single_instance = null;

    ArrayList<Train> departures = new ArrayList<Train>();
    ArrayList<Train> allTrains = new ArrayList<Train>();
    ArrayList<Train> trainsToBeSent = new ArrayList<Train>();
    ArrayList<Train> trainsWaiting = new ArrayList<Train>();
    ArrayList<Train> trainsSent = new ArrayList<Train>();
    String trainsSentText;


    private TrainSchedule() {
        System.out.println("++++++++++++++CESTOVNY PORIADOK VYTVORENY+++++++++++++++");
        trainsSentText = "";

        String[] ids = {"OS", "R", "IC", "REX"};
        String[] from = {"Košice", "Košice", "Bratislava", "Košice"};
        String[] to = {"Lipany", "Bratislava", "Košice", "Michalovce"};
        //int[] gap = {18, 50, 50, 18};
        int[] gap = {2, 5, 10, 5};

        Date time = new Date(new Date().getTime() - 1000 * 60 * Constants.MINUTE_GAP);
        time = DateUtils.truncate(time, Calendar.MINUTE);
        Date tempTime;
        int idFirst = 100;
        for (int j = 0; j < 4; j++) {
            tempTime = DateUtils.addMinutes(time, j * gap[j]);
            for (int i = 0; i < Constants.SPECIFIC_TRAIN_COUNT; i++) {
                int platNumber = 1 + i % 5;
                tempTime = DateUtils.addMinutes(time, i * gap[j]);
                Train train = new Train(ids[j] + (idFirst + i), platNumber, from[j], to[j], tempTime);
                train.updateDelayInMinutes();
                allTrains.add(train);
                if (from[j].equals("Košice")) {
                    departures.add(train);
                    if (!train.shouldBeGone()) {
                        trainsWaiting.add(train);
                    }
                }


            }
            idFirst += 100;
        }
        Collections.sort(allTrains, new TrainComparator());

        departures.sort(new TrainComparator());
        trainsWaiting.sort(new TrainComparator());
    }

    public static synchronized TrainSchedule getInstance() {
        if (single_instance == null) {
            single_instance = new TrainSchedule();
            //System.out.println("ktore vlakno vytvara singlton : " + Thread.currentThread().getId());
        }
        return single_instance;

    }


    public ArrayList<Train> getDepartures() {
        return departures;
    }

    public ArrayList<Train> getTrainsToBeSent() {
        return trainsToBeSent;
    }

    public ArrayList<Train> getTrainsWaiting() {
        return trainsWaiting;
    }

    public ArrayList<Train> getAllTrains() {
        return allTrains;
    }

    public String updateTrainsSentTxt(String trainId) {
        this.trainsSentText += " " + trainId;
        return trainsSentText;
    }


    public Train getTrainById(String id) {
        for (Train train : allTrains) {
            if (train.getId().equals(id)) {
                return train;
            }
        }
        System.out.println("train with false ID");
        return null;
    }


    public void refresh() {
        for (Train train : allTrains) {
            train.updateDelayInMinutes();
            if (train.shouldBeGone() && !(this.trainsSent.contains(train)) && !(this.trainsToBeSent.contains(train))) {
                trainsToBeSent.add(train);
                trainsWaiting.remove(train);
                trainsSent.add(train);
            }
            if (!train.shouldBeGone() && !trainsWaiting.contains(train)) {
                trainsWaiting.add(train);
            }

        }

        trainsToBeSent.sort(new TrainComparator());
        trainsWaiting.sort(new TrainComparator());

    }


}
