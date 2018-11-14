import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.Consumer;
import redis.clients.jedis.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import design.factory.*;
import tio.Io;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {

    public static void main(String[] args) throws Exception {
        //Io.nioReadFile("/tmp/test.txt");
        TimerDemo.task1Exec();
        TimerDemo.task2Exec();
    }

    public void time() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
        //Consumer<String> c = s -> System.out.println(s);
		//c.accept("hello lambda");
    }

    public void testIncr() throws Exception {
        Jedis redis  = RedisCon.getRedisCon();
        var threadList = new ArrayList<Thread>();
        Runnable runnable = new Runnable() {
            
            @Override
            public void run(){
                try {
                    Jedis redis  = RedisCon.getRedisCon();
                    redis.incr("incr");
                } catch (Exception e) {

                }
            }
        };

        for(int i = 0 ; i < 100; i++) {
            Thread thread = new Thread(runnable);
            threadList.add(thread);
            thread.run();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        System.out.println(redis.get("incr"));
    }

    public void testThread() throws InterruptedException  {
        var arrayList = new ArrayList<String>();
        var threadList = new ArrayList<Thread>();
        var httpThread = new HttpThread(arrayList);

        for (int i = 0; i < 100; i++) {
            var thread = new Thread(httpThread);
            threadList.add(thread);
            thread.start();
        }

        for (Thread t : threadList) {
            t.join();
        }

        System.out.println("length is " + arrayList.size());
    }
}


class StreamTest
{
    public static void wordList() {
        var wordList = new ArrayList<String>();
        wordList.add("a");
        wordList.add("b");
        wordList.add("c");
        List<String> output = wordList.stream().
            map(String::toUpperCase).
            collect(Collectors.toList());
        for (String v : output) {
            System.out.println(v);
        }    
    }
}

class Subscriber extends JedisPubSub {
    public Subscriber() {
    }

    public void onMessage(String channel, String message) {
        System.out.println(String.format("receive redis published message, channel %s, message %s", channel, message));
    }

    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d", 
                channel, subscribedChannels));
    }

    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d", 
                channel, subscribedChannels));

    }
}


class RedisCon {
    public static Properties parseProperties() throws Exception {
        var redisPropertiesFile = RedisCon.class.getResource("redis.properties").getFile();
        InputStream inputStream = new FileInputStream(redisPropertiesFile);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    public static Jedis getRedisCon() throws Exception {
        Properties properties = parseProperties();
        Jedis redis = new Jedis(properties.getProperty("hostname"), Integer.parseInt(properties.getProperty("port")));
        redis.auth(properties.getProperty("password"));
        redis.select(Integer.parseInt(properties.getProperty("database")));
        return redis;
    }
}

class HttpThread implements Runnable {

    private volatile ArrayList<String> arrayList; //volatile不能保证lock

    public HttpThread(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public void run() {
        synchronized(arrayList) {
            arrayList.add(get());
        }
    }

    public String get(){
        double rand = Math.random();
        return String.valueOf(rand);
    }
}
