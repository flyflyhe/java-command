import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo extends TimerTask {
    @Override
    public void run() {
        System.out.println("执行");
    }

    public static void exec() {
        TimerTask task = new TimerDemo();
        Timer timer = new Timer();
        timer.schedule(task, new Date(), 3000);
    }
}
