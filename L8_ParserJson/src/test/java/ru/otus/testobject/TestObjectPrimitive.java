package ru.otus.testobject;

public class TestObjectPrimitive {
    private byte _byte;
    private char _char;
    private short _short;
    private int _int;
    private long _long;
    private float _float;
    private double _double;
    private boolean _boolean;


    public TestObjectPrimitive() {
    }

    ;

    public TestObjectPrimitive(byte _byte, char _char, short _short, int _int, long _long, float _float, double _double, boolean _boolean) {
        this._byte = _byte;
        this._char = _char;
        this._short = _short;
        this._int = _int;
        this._long = _long;
        this._float = _float;
        this._double = _double;
        this._boolean = _boolean;
    }

    public byte get_byte() {
        return _byte;
    }

    public void set_byte(byte _byte) {
        this._byte = _byte;
    }

    public char get_char() {
        return _char;
    }

    public void set_char(char _char) {
        this._char = _char;
    }

    public short get_short() {
        return _short;
    }

    public void set_short(short _short) {
        this._short = _short;
    }

    public int get_int() {
        return _int;
    }

    public void set_int(int _int) {
        this._int = _int;
    }

    public long get_long() {
        return _long;
    }

    public void set_long(long _long) {
        this._long = _long;
    }

    public float get_float() {
        return _float;
    }

    public void set_float(float _float) {
        this._float = _float;
    }

    public double get_double() {
        return _double;
    }

    public void set_double(double _double) {
        this._double = _double;
    }

    public boolean is_boolean() {
        return _boolean;
    }

    public void set_boolean(boolean _boolean) {
        this._boolean = _boolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestObjectPrimitive that = (TestObjectPrimitive) o;

        if (_byte != that._byte) return false;
        if (_char != that._char) return false;
        if (_short != that._short) return false;
        if (_int != that._int) return false;
        if (_long != that._long) return false;
        if (Float.compare(that._float, _float) != 0) return false;
        if (Double.compare(that._double, _double) != 0) return false;
        return _boolean == that._boolean;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) _byte;
        result = 31 * result + (int) _char;
        result = 31 * result + (int) _short;
        result = 31 * result + _int;
        result = 31 * result + (int) (_long ^ (_long >>> 32));
        result = 31 * result + (_float != +0.0f ? Float.floatToIntBits(_float) : 0);
        temp = Double.doubleToLongBits(_double);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (_boolean ? 1 : 0);
        return result;
    }
}
