public class DelayInfoMessage {
    int delay;
    String id;


    public DelayInfoMessage(String id, int delay) {
        this.id = id;
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public String getId() {
        return id;
    }
}
