package ru.otus;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import ru.otus.arraylist.MyArrayList;

/**
 * Created by tully.
 *
 * Run examples: mvn package java -cp target/l1.maven.jar Main java -cp
 * "target/l1.maven.jar:/home/tully/.m2/repository/net/sf/opencsv/opencsv/2.3/opencsv-2.3.jar" Main
 * java -jar target/l1.maven.jar
 */
public class Main {

  public static void main(String[] args) throws IOException {
    MyArrayList<Integer> myArrayList = new MyArrayList<>();
    Collections.addAll(myArrayList, 4, 99, 15, 16, 23, 42);
    //System.out.println("Create myArrayList");
    // myArrayList.stream().forEach(System.out::println);
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
