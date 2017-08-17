package parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.TestObjectPrimitive;
import ru.otus.parser.IParser;
import ru.otus.parser.Parser;

public class ParserTestPrimitive {

    private static final String JSON_PRIMETIVE_OBJECT = "{\"_byte\":126,\"_char\":1074,\"_short\":1123,\"_int\":10,\"_long\":100,\"_float\":4.400000095367432,\"_double\":6.7,\"_boolean\":true}";
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
    public void createJsonFromObjectWithPrimetive() throws IllegalAccessException, ClassNotFoundException {
        String json = parser.jsonFromObject(initialObj);
        Assert.assertTrue(json.equals(JSON_PRIMETIVE_OBJECT));
    }
}
