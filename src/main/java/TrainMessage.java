public class TrainMessage {
    private String id;
    //private  String toCity;

    //private int delay;
    private int platform;
    private long time;

    public TrainMessage(String id, int platform, long time) {
        this.id = id;
        this.platform = platform;
        this.time = time;


    }


    public String getId() {
        return id;
    }

    public int getPlatform() {
        return platform;
    }

    public long getTime() {
        return time;
    }


}
