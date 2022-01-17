import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class Train {
    private String id;
    private long delay;
    private long supposedDelay;
    private int platform;
    private String fromCity;


    private String toCity;
    private Date realArrivalDepartureTime;
    private Date plannedArrivalDepartureTime;


    public Train(String id, int platform, String fromCity, String toCity, Date plannedArrivalDepartureTime) {
        this.id = id;
        this.platform = platform;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.plannedArrivalDepartureTime = plannedArrivalDepartureTime;
        this.realArrivalDepartureTime = new Date(plannedArrivalDepartureTime.getTime());
        // testing
        this.supposedDelay = 0;

    }

    public boolean isArriving() {
        return this.toCity.equals("Košice");
    }


    public long getDelayInMinutesFromMessageTime(long realTime) {
        // real time mostly now()
        long timeDiff = (realTime - this.plannedArrivalDepartureTime.getTime()) / (1000 * 60);
        long tempDelay = timeDiff < 0 ? 0 : timeDiff;
        long supposedDelay = getSupposedDelay() / (1000 * 60);
        if ((timeDiff < 0 && (supposedDelay + timeDiff > 0)) || (timeDiff > 0 && (supposedDelay > timeDiff))) {
            tempDelay = supposedDelay;
        }
        this.delay = tempDelay;
        return tempDelay;
    }

    public void updateDelayInMinutes() {
        // real time mostly now()
        long timeDiff = ((new Date().getTime()) - this.plannedArrivalDepartureTime.getTime()) / (1000 * 60);
        //System.out.println(timeDiff);
        // time diff väčšie ako 0 ak vlak už mal odist
        long tempDelay = timeDiff < 0 ? 0 : timeDiff;
        long supposedDelay = getSupposedDelay() / (1000 * 60);
        if ((timeDiff < 0 ) || (timeDiff > 0 && (supposedDelay > timeDiff)) || isTherePlannedDelay() ) {
            tempDelay = supposedDelay;
        }
        this.delay = tempDelay;
        //System.out.println(this.delay);

    }

    public long getSupposedDelay() {

        return this.realArrivalDepartureTime.getTime() - this.plannedArrivalDepartureTime.getTime();
    }
    public boolean isTherePlannedDelay() {

        return this.realArrivalDepartureTime.getTime() != this.plannedArrivalDepartureTime.getTime();
    }

    public boolean shouldBeGone() {
        //System.out.println("should be gone");
        return this.getRealArrivalDepartureTime().getTime() < new Date().getTime();
    }

    public void updateAfterPlannedDelay(int delay) {

        this.setRealArrivalDepartureTime(DateUtils.addMinutes(this.getPlannedArrivalDepartureTime(), delay));
        this.updateDelayInMinutes();
    }

    public String getId() {
        return id;
    }

    public long getDelay() {
        return delay;
    }

    public int getPlatform() {
        return platform;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public Date getRealArrivalDepartureTime() {
        return realArrivalDepartureTime;
    }

    public void setRealArrivalDepartureTime(Date realArrivalDepartureTime) {
        this.realArrivalDepartureTime = realArrivalDepartureTime;
    }

    public Date getPlannedArrivalDepartureTime() {
        return plannedArrivalDepartureTime;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }




    /*@Override
    public String toString() {
        String timeMessage = isArriving() ? " planned arrival : " : " planned departure : ";
        timeMessage = timeMessage + plannedArrivalDepartureTime.toString();

        String timeMessage2 = isArriving() ? " real arrival : " : " real departure : ";
        timeMessage2 = timeMessage2 + realArrivalDepartureTime.toString();
        return "_________________________________________\nTrain:  " +
                "id='" + id + '\n' +
                ", delay=" + delay + "\n" +
                ", platform=" + platform + "\n" +
                ", fromCity='" + fromCity + '\n' +
                ", toCity='" + toCity + '\n' +
                timeMessage + "\n" +
                timeMessage2
                ;
    }*/

    @Override
    public String toString() {
        String timeMessage = isArriving() ? "planned arrival : " : " planned departure : ";
        timeMessage = timeMessage + plannedArrivalDepartureTime.toString();

        String timeMessage2 = isArriving() ? "real arrival : " : " real departure : ";
        timeMessage2 = timeMessage2 + realArrivalDepartureTime.toString();
        return "_________________________________________\nTrain:  " +
                "id='" + id + '\n' +
                "delay=" + delay + "\n" +
                timeMessage + "\n" +
                timeMessage2
                ;
    }
}
