import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import redis.clients.jedis.*;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {

    public static void main(String[] args) throws Exception {
        var app = new App();
        app.testIncr();
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
