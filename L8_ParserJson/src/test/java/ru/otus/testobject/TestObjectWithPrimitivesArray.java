package ru.otus.testobject;

import java.util.Arrays;

public class TestObjectWithPrimitivesArray extends TestObjectPrimitive {

    private int[] _intArray;
    private double[] _doubleArray;
    private float[] _floatArray;
    private boolean[] _booleanArray;
    private byte[] _byteArray;
    private char[] _charArray;

    public TestObjectWithPrimitivesArray() {
    }

    public TestObjectWithPrimitivesArray(byte _byte, char _char, short _short,
                                         int _int, long _long, float _float,
                                         double _double, boolean _boolean,
                                         int[] _intArray, double[] _doubleArray,
                                         float[] _floatArray,
                                         boolean[] _booleanArray,
                                         byte[] _byteArray, char[] _charArray) {
        super(_byte, _char, _short, _int, _long, _float, _double, _boolean);
        this._intArray = _intArray;
        this._doubleArray = _doubleArray;
        this._floatArray = _floatArray;
        this._booleanArray = _booleanArray;
        this._byteArray = _byteArray;
        this._charArray = _charArray;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TestObjectWithPrimitivesArray that = (TestObjectWithPrimitivesArray) o;

        return Arrays.equals(_intArray, that._intArray);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(_intArray);
        return result;
    }
}
