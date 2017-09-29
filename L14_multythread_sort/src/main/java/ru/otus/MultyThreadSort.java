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
        fillList(arr);
        exec();
        Integer[] res = fillRes(arr.length);
       // System.out.println("FutureTask1 output=" + Arrays.toString(res));
        //Arrays.sort(res);
        return res;
    }

    private Integer[] fillRes(Integer numOfElements) {
        boolean isDone = true;
        int count = 1;
        int startFillArr = 0;
        Integer[] tempRes=null;
        while (true) {
            isDone = true;
            try {
                for (FutureTask<Integer[]> task : futureTasks) {
                    if (!task.isDone()) {
                        isDone = false;
                    }
                }

                Integer[] temp;
                Iterator iter = futureTasks.iterator();
                if (isDone) {
                    while (iter.hasNext()) {
                        FutureTask<Integer[]> task = (FutureTask<Integer[]>) iter.next();
                        temp = task.get();
                        if (temp != null) {
                            task.cancel(true);
                            count++;
                            /*
                            System.arraycopy(temp, 0, res, startFillArr, temp.length);
                            startFillArr += temp.length;
                            Arrays.sort(res);
                            */
                            if(tempRes != null){
                                if(tempRes.length> temp.length){
                                    tempRes = merge(temp, tempRes);
                                }else{
                                    tempRes = merge(tempRes, temp);
                                }
                            }else{
                                tempRes =temp;
                            }
                           // System.out.println("FutureTask output=" + Arrays.toString(temp));
                            iter.remove();
                        }
                    }

                }

                if (tempRes != null && tempRes.length == numOfElements) {
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
        return tempRes;
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
    private Integer[] merge(Integer[] left, Integer[] right) {

        int leftSize = left.length;
        int rightSize = right.length;
        Integer[] arr = new Integer[leftSize+rightSize];
        int i = 0, j = 0, k = 0;
        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
                k++;
            } else {
                arr[k] = right[j];
                k++;
                j++;
            }
        }
        while (i < leftSize) {
            arr[k] = left[i];
            k++;
            i++;
        }
        while (j < rightSize) {
            arr[k] = right[j];
            k++;
            j++;
        }
        return arr;
    }
}
