package ru.otus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class MultyThreadSort {
    private static final int NUMBER_OF_THREAD = 4;
    private List<FutureTask<Integer[]>> futureTasks = new ArrayList<FutureTask<Integer[]>>();
    private ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public Integer[] sort(Integer[] arr) throws InterruptedException, ExecutionException {
        if (arr.length == 1) {
            return arr;
        }
        Integer[] res = new Integer[arr.length];
        fillList(arr);
        exec();
        fillRes(res);
       // System.out.println("FutureTask1 output=" + Arrays.toString(res));
        Arrays.sort(res);
        return res;
    }

    private void fillRes(Integer[] res) {
        boolean isDone = true;
        int count = 1;
        int startFillArr = 0;
        while (true) {
            isDone = true;
            try {
                for (FutureTask<Integer[]> task : futureTasks) {
                    if (!task.isDone()) {
                        isDone = false;
                    }
                }

                Iterator iter = futureTasks.iterator();
                if (isDone) {
                    while (iter.hasNext()) {
                        FutureTask<Integer[]> task = (FutureTask<Integer[]>) iter.next();
                        Integer[] temp = task.get();
                        if (temp != null) {
                            task.cancel(true);
                            count++;
                            System.arraycopy(temp, 0, res, startFillArr, temp.length);
                            startFillArr += temp.length;
                           // System.out.println("FutureTask output=" + Arrays.toString(temp));
                            iter.remove();
                        }
                    }

                }
                if (startFillArr >= res.length) {
                    ;
                    ;
                    executor.shutdown();
                    break;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
//          catch (TimeoutException e) {
//                // do nothing
//            }
        }
    }

    private void exec() {
        for (FutureTask<Integer[]> task : futureTasks) {
            executor.execute(task);
        }
    }

    private void fillList(Integer[] arr) {
        int range = arr.length / NUMBER_OF_THREAD;
        int len = arr.length;
        int processed =0;
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            int lenOfTempArr = ((i+1)==NUMBER_OF_THREAD) ? (len - processed) : range;
            Integer[] temp = new Integer[lenOfTempArr];
            System.arraycopy(arr, i * range, temp, 0, temp.length);
            SortWoorker callable = new SortWoorker(temp);
            FutureTask<Integer[]> task = new FutureTask<Integer[]>(callable);
            futureTasks.add(task);
            processed +=range;
        }
    }
}
