package ru.otus.arraylist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Serdar on 18.06.2017.
 */
public class MyArrayList<T> implements List<T> {

  private static final Object[] EMPTY_ELEMENTS = {};
  private static final int CAPACITY_DEF = 15;
  private Object[] elements;
  private int capacity;
  private int size;


  public MyArrayList() {
    this(CAPACITY_DEF);
  }

  public MyArrayList(int capacity) {
    this.size = 0;
    this.capacity = capacity;
    elements = new Object[capacity];
    Supplier<Object> supplier = () -> new Object();
    elements = Stream.generate(supplier).limit(capacity).toArray(Object[]::new);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return (elements == null) ? true : false;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return new ListIter(0);
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elements, size);
  }

  @Override
  public <T1> T1[] toArray(T1[] a) {
    if (a.length < size) {
      return (T1[]) Arrays.copyOf(elements, size, a.getClass());
    }
    System.arraycopy(elements, 0, a, 0, size);
    if (a.length > size) {
      a[size] = null;
    }
    return a;
  }

  @Override
  public boolean add(T t) {
    checkNeedEncrease(1);
    elements[size++] = t;
    return true;
  }

  private void checkNeedEncrease(int needToAddElementsCount) {
    int newSize = size + needToAddElementsCount;
    if (elements.length <= newSize) {
      long newCapacityL = elements.length * 3;
      int newCapacity = 0;
      if (newCapacityL > Integer.MAX_VALUE) {
        newCapacity = Integer.MAX_VALUE;
      } else {
        newCapacity = Math.toIntExact(newCapacityL);
      }
      elements = Arrays.copyOf(elements, newCapacity);
    }
  }

  @Override
  public boolean remove(Object o) {
    long cnt = Arrays.stream(elements).filter((s) -> !s.equals(o)).count();
    if (cnt > 0) {
      elements = Arrays.stream(elements).filter((s) -> !s.equals(o)).toArray();
      size -= Math.toIntExact(cnt);
      return true;
    }
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    addAll(size, c);
    return false;
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    if (index > size) {
      return false;
    }
    if (c.size() + size > Integer.MAX_VALUE) {
      return false;
    }
    checkNeedEncrease(c.size());
    Object[] tempArr = c.toArray();
    System.arraycopy(tempArr, 0, elements, index, tempArr.length);
    size += tempArr.length;
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {

  }

  @Override
  public T get(int index) {
    if (index > elements.length) {
      return null;
    }
    return (T) elements[index];
  }

  @Override
  public T set(int index, T element) {
    if (elements.length < index) {
      checkNeedEncrease(index + 1);
    }
    elements[index] = element;
    return element;
  }

  @Override
  public void add(int index, T element) {
    set(index, element);
  }


  @Override
  public T remove(int index) {
    if (index > size) {
      return null;
    }
    T oldVal = (T) elements[index];
    System.arraycopy(elements, index + 1, elements, index,
        size - index - 1);
    return oldVal;
  }

  @Override
  public int indexOf(Object o) {
    if (o == null) {
      for (int i = 0; i < size; i++) {
        if (elements[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (o.equals(elements[i])) {
          return i;
        }
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
    if (o == null) {
      for (int i = size - 1; i >= 0; i--) {
        if (elements[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = size - 1; i >= 0; i--) {
        if (o.equals(elements[i])) {
          return i;
        }
      }
    }
    return -1;
  }

  @Override
  public ListIterator<T> listIterator() {
    return new ListIter(0);
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    return new ListIter(index);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    if ((fromIndex > 0 && fromIndex < size) && (toIndex > fromIndex && fromIndex <= size)) {
      Object[] objTemp = new Object[toIndex - fromIndex];
      System.arraycopy(elements, fromIndex, objTemp, 0, objTemp.length);
      List<T> list = (List<T>) Arrays.asList(objTemp);
      return list;
    }
    return null;
  }

  private class ListIter implements ListIterator<T> {

    int cursor;

    ListIter(int index) {
      cursor = index;
    }

    @Override
    public boolean hasNext() {
      return cursor < size;
    }

    @Override
    public T next() {
      return (T) elements[cursor++];
    }

    public boolean hasPrevious() {
      return cursor != 0;
    }

    public int nextIndex() {
      return cursor;
    }

    public int previousIndex() {
      return cursor - 1;
    }

    @Override
    public T previous() {
      int i = cursor - 1;
      Object[] elements = MyArrayList.this.elements;
      return (T) elements[i];
    }

    @Override
    public void remove() {
      MyArrayList.this.remove(cursor);
      cursor--;
    }

    @Override
    public void set(T t) {
      MyArrayList.this.set(cursor, t);
    }

    @Override
    public void add(T t) {
      MyArrayList.this.add(t);
      cursor++;
    }

  }
}
