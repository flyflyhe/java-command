import java.util.ArrayList;
import java.util.List;

public class GenericDemo<T extends List, E extends List> {
    public T t;
    public E e;

    public GenericDemo(T t, E e) {
        this.t = t;
        this.e = e;
    }

    public static void test() {
        var stringArrayList = new ArrayList<String>();
        var integerArrayList = new ArrayList<Integer>();
        GenericDemo<ArrayList<String>, ArrayList<Integer>> demo = new GenericDemo<>(stringArrayList, integerArrayList);
        demo.t.add("one");
        System.out.println(demo.t.get(0));
    }
}
