import ru.otus.MultyThreadSort;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by skomorokhov on 10.07.2017.
 */
public class Main {
    public static void main(String... args) throws ExecutionException, InterruptedException {
        Integer[] arr = {44, 11, 2, 1, 33, 3, 45, 6, 66, 6, 3};
        Integer[] res;
        MultyThreadSort multyThreadSort = new MultyThreadSort();
        res = multyThreadSort.sort(arr);
        System.out.println(" output=" + Arrays.toString(res));
        multyThreadSort = new MultyThreadSort();
        arr = new Integer[1_000_000];
        //arr = new Integer[11];
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt();
        }

        long start = System.nanoTime();
        Arrays.sort(arr);
        long end = System.nanoTime();
        System.out.println("Execute Arrays sort for " + (end - start) + "ns");

        start = System.nanoTime();
        multyThreadSort.sort(arr);
        end = System.nanoTime();

        System.out.println("Execute multyThreadSort sort for " + (end - start) + "ns");


    }
}
