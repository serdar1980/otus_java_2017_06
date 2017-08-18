package ru.otus.testobject;

import java.util.Arrays;

public class TestObjectWithRefArray {
    String[] _stringArray;
    TestObjectPrimitive[] testObjectPrimitives;

    public TestObjectWithRefArray() {
    }

    ;

    public TestObjectWithRefArray(String[] _stringArray,
                                  TestObjectPrimitive[] testObjectPrimitives) {
        this._stringArray = _stringArray;
        this.testObjectPrimitives = testObjectPrimitives;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestObjectWithRefArray that = (TestObjectWithRefArray) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(_stringArray, that._stringArray)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(testObjectPrimitives, that.testObjectPrimitives);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(_stringArray);
        result = 31 * result + Arrays.hashCode(testObjectPrimitives);
        return result;
    }
}
