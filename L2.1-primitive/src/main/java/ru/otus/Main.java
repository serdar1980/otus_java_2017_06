package ru.otus;

import java.util.function.Supplier;

class Main {

    static void countMemory(int count, Supplier<?> supplier) throws Exception {
        Runtime r=Runtime.getRuntime();
        r.gc();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new Exception(e);
        }
        long beforeUsedMem =  r.totalMemory() - r.freeMemory();
        Object[] result = new Object[count];
        for (int n = 0; n < count; n++) {
            result[n] = supplier.get();
        }
        long afterUsedMem = r.totalMemory() - r.freeMemory();
        long actualMemUsed = afterUsedMem - beforeUsedMem;
        StringBuilder sb = new StringBuilder();
        sb.append("Average memory for object: ").append(result[0].getClass().getCanonicalName())
            .append("\r\n").append(supplier.get())
            .append("\r\n").append("size: ").append(actualMemUsed / count);
        System.out.println(sb.toString());
        r.gc();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new Exception(e);
        }
    }


    public static void main(String[] args) throws Exception {
        countMemory(100, Main::new);
        countMemory(100, Object::new);
        countMemory(100, String::new);
        countMemory(100, () -> new String(""));
        countMemory(100, () -> new String("Вася"));
        countMemory(100, () -> new Long(10L));
        countMemory(100, () -> new Integer(10));
        countMemory(100, () -> new Double(10));
    }

}

