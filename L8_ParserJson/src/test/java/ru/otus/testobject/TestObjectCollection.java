package ru.otus.testobject;

import java.util.*;

public class TestObjectCollection {

    private List<Integer> _list = new ArrayList<>();
    private Set<Integer> _set = new HashSet<>();
    private Map<String, Integer> _map = new HashMap<>();

    public TestObjectCollection() {
    }

    ;

    public TestObjectCollection(List<Integer> _list,
                                Set<Integer> _set,
                                Map<String, Integer> _map) {
        this._list = _list;
        this._set = _set;
        this._map = _map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestObjectCollection that = (TestObjectCollection) o;

        if (_list != null ? !_list.equals(that._list) : that._list != null)
            return false;
        if (_set != null ? !_set.equals(that._set) : that._set != null)
            return false;
        return _map != null ? _map.equals(that._map) : that._map == null;
    }

    @Override
    public int hashCode() {
        int result = _list != null ? _list.hashCode() : 0;
        result = 31 * result + (_set != null ? _set.hashCode() : 0);
        result = 31 * result + (_map != null ? _map.hashCode() : 0);
        return result;
    }
}
