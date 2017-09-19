package ru.otus;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class SortWoorker implements Callable<Integer[]> {

    private long waitTime;
    private Integer[] arr;

    public SortWoorker(int timeInMillis, Integer[] arr){
        this.waitTime=timeInMillis;
        this.arr =arr;
    }
    public SortWoorker( Integer[] arr){
        this(0, arr);
    }
    @Override
    public Integer[] call() throws Exception {
        Thread.sleep(waitTime);
        Arrays.sort(arr);
        //return the thread name executing this callable task
        return arr;
    }

}