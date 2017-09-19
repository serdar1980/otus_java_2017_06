package ru.otus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MultyThreadSortTest {
    MultyThreadSort multyThreadSort;

    @Test
    public void sortTest() throws ExecutionException, InterruptedException {
        Integer[] arr = {44, 11, 2, 1, 33, 3, 45, 6, 66, 6, 3};
        Integer[] arr1 = {44, 2, 1, 33, 3, 45, 6, 66, 6, 3, 11};

        Assert.assertFalse(Arrays.equals(arr, arr1));
        Arrays.sort(arr);
        multyThreadSort = new MultyThreadSort();
        arr1 = multyThreadSort.sort(arr1);
        Assert.assertTrue(Arrays.equals(arr, arr1));
    }
}
