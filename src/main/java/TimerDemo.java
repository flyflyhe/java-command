import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo{

    public static void task1Exec() {
        TimerTask task = new Task1();
        Timer timer = new Timer();
        timer.schedule(task, new Date(), 3000);
    }

    public static void task2Exec() {
        TimerTask task = new Task2();
        Timer timer = new Timer();
        timer.schedule(task, new Date(), 3000);
    }
}

class Task1 extends TimerTask {
    @Override
    public void run() {
        System.out.println("task1执行");
    }
}

class Task2 extends TimerTask {
    @Override
    public void run() {
        System.out.println("task2执行");
    }
}
