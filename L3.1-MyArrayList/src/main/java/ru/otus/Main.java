package ru.otus;

import java.io.IOException;
import java.util.Collections;
import ru.otus.arraylist.MyArrayList;

public class Main {

  public static void main(String[] args) throws IOException {
    MyArrayList<Integer> myArrayList = new MyArrayList<>();
    Collections.addAll(myArrayList, 4, 99, 15, 16, 23, 42);
    MyArrayList<Integer> myArrayListCopy = new MyArrayList<>();
    Collections.addAll(myArrayListCopy, 89, 78, 45, 12, 45, 78, 5, 5, 5, 75, 7, 125, 125);
    Collections.copy(myArrayListCopy, myArrayList);
    System.out.println("After copy");
    myArrayListCopy.stream().forEach(System.out::println);
    Collections
        .sort(myArrayListCopy, (Integer o1, Integer o2) -> Integer.compare(o1,o2));
    System.out.println("After sort");
    myArrayListCopy.stream().forEach(System.out::println);
  }

}
