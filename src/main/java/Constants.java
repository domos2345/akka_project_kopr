import java.util.Date;

public class Constants {
    public static final int TRAIN_COUNT = 20;
    public static final int SPACING = 15;
    public static final int BIG_SPACING = new Date().toString().length() + 5;
    public static final int MINUTE_GAP = 30;
    public static final int SPECIFIC_TRAIN_COUNT = 15;


    public static String printWithSpacing(String s, int spacing) {
        return s + " ".repeat(spacing - s.length());
    }
}


