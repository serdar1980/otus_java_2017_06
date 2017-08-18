package ru.otus.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.testobject.TestObjectCollection;
import ru.otus.testobject.TestObjectPrimitive;
import ru.otus.testobject.TestObjectWithRefArray;
import ru.otus.parser.IParser;
import ru.otus.parser.Parser;

public class PaerserTestRefArray {
    private static final String JSON_STRING = "{\"_stringArray\":[\"Foo\",\"Bar\",\"Baz\"],\"testObjectPrimitives\":[{\"_byte\":126,\"_char\":\"в\",\"_short\":1123,\"_int\":10,\"_long\":100,\"_float\":4.400000095367432,\"_double\":6.7,\"_boolean\":true},{\"_byte\":126,\"_char\":\"в\",\"_short\":1123,\"_int\":20,\"_long\":200,\"_float\":4.400000095367432,\"_double\":6.7,\"_boolean\":false}]}";
    TestObjectWithRefArray initialObj;
    IParser parser = new Parser();

    @Before
    public void startUp() {
        byte b = 126;
        short s = 1123;
        TestObjectPrimitive objectPrimitive = new TestObjectPrimitive(b,
                'в', s, 10, 100L,
                4.4F, 6.7D, true);
        TestObjectPrimitive objectPrimitive1 = new TestObjectPrimitive(b,
                'в', s, 20, 200L,
                4.4F, 6.7D, false);

        initialObj = new TestObjectWithRefArray(
                new String[]{"Foo", "Bar", "Baz"},
                new TestObjectPrimitive[]{objectPrimitive, objectPrimitive1}
        );
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
        System.out.println(json);
        Gson gsonTO = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String gsonStr = gsonTO.toJson(initialObj);
        Assert.assertTrue(json.equals(JSON_STRING));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        TestObjectWithRefArray testObjectWithRefArrayFromJson =
                gson.fromJson(json, TestObjectWithRefArray.class);
        Assert.assertTrue(testObjectWithRefArrayFromJson.equals(initialObj));
    }


}
