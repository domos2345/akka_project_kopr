import java.util.Comparator;

public class TrainComparator implements Comparator<Train> {
    @Override
    public int compare(Train o1, Train o2) {
        Long time1, time2;
        time1 = o1.getRealArrivalDepartureTime().getTime() ;
        time2 = o2.getRealArrivalDepartureTime().getTime() ;


        return time1.compareTo(time2);
    }
}
