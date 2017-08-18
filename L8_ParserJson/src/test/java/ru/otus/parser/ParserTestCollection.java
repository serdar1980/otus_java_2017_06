package ru.otus.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.testobject.TestObjectCollection;
import ru.otus.testobject.TestObjectPrimitive;
import ru.otus.testobject.TestObjectWithPrimitivesArray;

import java.util.*;

public class ParserTestCollection {
    private static final String JSON_STRING = "{\"_list\":[113,114,115,113],\"_set\":[113,114,115],\"_map\":{\"Hello\":1,\"Test\":3,\"World\":5}}";
    TestObjectCollection initialObj;
    IParser parser = new Parser();

    @Before
    public void startUp() {
        List<Integer> _list = new ArrayList<>();
        _list.add(113);
        _list.add(114);
        _list.add(115);
        _list.add(113);

        Set<Integer> _set = new HashSet<>(_list);
        Map<String, Integer> _map = new HashMap<>();
        _map.put("Hello", 1);
        _map.put("Test", 3);
        _map.put("World", 5);
        initialObj = new TestObjectCollection(_list, _set, _map);
    }

    @Test
    public void createObjectWithPrimetiveFromJson() {
        TestObjectPrimitive res = (TestObjectPrimitive)
                parser.ObjectFromJson(JSON_STRING);
        Assert.assertTrue(res.equals(initialObj));
    }

    @Test
    public void createJsonFromObjectWithPrimetive() throws IllegalAccessException, ClassNotFoundException {
        String json = parser.jsonFromObject(initialObj);
        Gson gsonTO = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String gsonStr = gsonTO.toJson(initialObj);
        Assert.assertTrue(json.equals(JSON_STRING));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        TestObjectCollection testObjectWithCollectionFromJson =
                gson.fromJson(json, TestObjectCollection.class);
        Assert.assertTrue(testObjectWithCollectionFromJson.equals(initialObj));
    }

}
