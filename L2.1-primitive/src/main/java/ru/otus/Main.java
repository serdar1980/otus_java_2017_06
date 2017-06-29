package ru.otus;

import java.util.function.Supplier;

class Main {

  static void countMemory(int count, Supplier<?> supplier) throws Exception {
    Runtime r = Runtime.getRuntime();
    r.gc();
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new Exception(e);
    }

    long actualMemUsedSum=0L;
    Object[] result = new Object[count];
    for (int n = 0; n < count; n++) {
      long beforeUsedMem = r.totalMemory() - r.freeMemory();
      r.gc();
      result[n] = supplier.get();
      long afterUsedMem = r.totalMemory() - r.freeMemory();
      long actualMemUsed = afterUsedMem - beforeUsedMem;
      actualMemUsedSum +=actualMemUsed;

    }
    StringBuilder sb = new StringBuilder();
    sb.append("Average memory for object: ").append(result[0].getClass().getCanonicalName())
        .append("\r\n").append(supplier.get())
        .append("\r\n").append("size: ").append(actualMemUsedSum / count);
    System.out.println(sb.toString());
    r.gc();
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new Exception(e);
    }
  }


  public static void main(String[] args) throws Exception {
    int count = 1999;
    countMemory(count, Main::new);
    countMemory(count, Object::new);
    countMemory(count, String::new);
    countMemory(count, () -> new String(""));
    countMemory(count, () -> new String("Вася"));
    countMemory(count, () -> new Long(10L));
    countMemory(count, () -> new Integer(10));
    countMemory(count, () -> new Double(10));
  }

}

