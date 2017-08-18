package ru.otus.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.testobject.TestObjectWithPrimitivesArray;

public class ParserTestPrimetivesArray {
    private static final String JSON_STRING = "{\"_intArray\":[1,2,3,4,5,6],\"_doubleArray\":[4.5,5.6],\"_floatArray\":[9.5,9.6],\"_booleanArray\":[true,false],\"_byteArray\":[1,2],\"_charArray\":[\"c\",\"g\"],\"_byte\":126,\"_char\":\"в\",\"_short\":1123,\"_int\":10,\"_long\":100,\"_float\":4.400000095367432,\"_double\":6.7,\"_boolean\":true}";
    IParser parser = new Parser();
    TestObjectWithPrimitivesArray initialObj;

    @Before
    public void startUp() {
        byte b = 126;
        short s = 1123;
        int[] _intArray = {1, 2, 3, 4, 5, 6};
        double[] _doubleArray = {4.5D, 5.6D};
        float[] _floatArray = {9.5F, 9.6F};
        boolean[] _booleanArray = {true, false};
        byte[] _byteArray = {1, 2};
        char[] _charArray = {'c', 'g'};
        initialObj = new TestObjectWithPrimitivesArray(b,
                'в', s, 10, 100L,
                4.4F, 6.7D, true, _intArray, _doubleArray,
                _floatArray, _booleanArray,
                _byteArray, _charArray);
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
        TestObjectWithPrimitivesArray testObjectWithPrimitivesArrayFromJson =
                gson.fromJson(json, TestObjectWithPrimitivesArray.class);
        Assert.assertTrue(testObjectWithPrimitivesArrayFromJson.equals(initialObj));
    }
}