package ru.otus.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.gson.CharacterDeserializer;
import ru.otus.testobject.TestObjectPrimitive;
import ru.otus.parser.IParser;
import ru.otus.parser.Parser;

public class ParserTestPrimitive {

    private static final String JSON_PRIMETIVE_OBJECT = "{\"_byte\":126,\"_char\":\"в\",\"_short\":1123,\"_int\":10,\"_long\":100,\"_float\":4.400000095367432,\"_double\":6.7,\"_boolean\":true}";
    IParser parser = new Parser();
    TestObjectPrimitive initialObj;

    @Before
    public void startUp() {
        byte b = 126;
        short s = 1123;
        initialObj = new TestObjectPrimitive(b,
                'в', s, 10, 100L,
                4.4F, 6.7D, true);
    }

    @Test
    public void createObjectWithPrimetiveFromJson() {
        TestObjectPrimitive res = (TestObjectPrimitive)
                parser.ObjectFromJson(JSON_PRIMETIVE_OBJECT);
        Assert.assertTrue(res.equals(initialObj));
    }

    @Test
    public void createJsonFromObjectWithPrimetive()
            throws IllegalAccessException, ClassNotFoundException {
        String json = parser.jsonFromObject(initialObj);
        Assert.assertTrue(json.equals(JSON_PRIMETIVE_OBJECT));
        Gson gsonTO = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String gsonStr = gsonTO.toJson(initialObj);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        TestObjectPrimitive testObjectPrimitiveFromJson =
                gson.fromJson(json, TestObjectPrimitive.class);
        Assert.assertTrue(testObjectPrimitiveFromJson.equals(initialObj));
    }
}
