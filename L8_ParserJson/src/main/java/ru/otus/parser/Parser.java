package ru.otus.parser;

import utils.Types;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Parser implements IParser {
    Map<String, Object> config = new HashMap<String, Object>();
    JsonBuilderFactory factory = Json.createBuilderFactory(config);
    JsonObjectBuilder objectBuilder = factory.createObjectBuilder();


    @Override
    public String jsonFromObject(Object obj) throws IllegalAccessException, ClassNotFoundException {
        config.put("javax.json.stream.JsonGenerator.prettyPrinting", Boolean.valueOf(true));
        return parseObject(obj).build().toString();
    }

    private JsonObjectBuilder parseObject(Object obj) throws IllegalAccessException, ClassNotFoundException {
        Class<?> current = obj.getClass();
        while (current.getSuperclass() != null) {
            Field[] fields = current.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName());
                if (field.getType().isPrimitive()) {
                    addToJsonObjectBuilder(objectBuilder, field, obj);
                } else {
                    String name = field.getName();
                    if (field.getType().isArray()) {
                        //TODO method create array json
                        addToJsonObjectBuilder(objectBuilder, field, obj);
                        System.out.println("ARRAY return");
                    }
                }
            }
            current = current.getSuperclass();

        }

        System.out.println("return Object builder");
        return objectBuilder;
    }

    private void addToJsonObjectBuilder(JsonObjectBuilder value, Field field, Object obj) throws IllegalAccessException, ClassNotFoundException {
        String name = field.getName();
        Types types = Types.getType(field.getType());
        field.setAccessible(true);
        switch (types) {
            case BYTE:
                value.add(name, field.getByte(obj));
                break;
            case BOOLEAN:
                value.add(name, field.getBoolean(obj));
                break;
            case SHORT:
                value.add(name, field.getShort(obj));
                break;
            case CHAR:
                value.add(name, field.getChar(obj));
                break;
            case INT:
                value.add(name, field.getInt(obj));
                break;
            case FLOAT:
                value.add(name, field.getFloat(obj));
                break;
            case LONG:
                value.add(name, field.getLong(obj));
                break;
            case DOUBLE:
                value.add(name, field.getDouble(obj));
                break;
            case ARRAY:
                JsonArrayBuilder jsonArray = getJsonArrayBuilder(field, obj);
                value.add(name, jsonArray.build());
                break;
            default:
                throw new UnsupportedOperationException("Not primetive type");
        }
        field.setAccessible(false);
    }

    private JsonArrayBuilder getJsonArrayBuilder(Field field, Object obj) throws IllegalAccessException {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilderArr = factory.createObjectBuilder();
        System.out.println("ARRAY");
        Object array = field.get(obj);
        int len = Array.getLength(array);
        for (int i = 0; i < len; i++) {
            Object val = Array.get(array, i);
            if (val instanceof Number) {
                if (val instanceof Float || val instanceof Double) {
                    jsonArray.add(Double.valueOf(val.toString()));
                } else {
                    jsonArray.add(Long.valueOf(val.toString()));
                }
            } else if (val instanceof String) {
                jsonArray.add((String) val);
            } else if (val instanceof Boolean) {
                jsonArray.add((Boolean) val);
            } else if (val instanceof Character) {
                jsonArray.add((Character) val);
            }

        }
        return jsonArray;
    }


    @Override
    public Object ObjectFromJson(String json) {
        throw new UnsupportedOperationException("метод не готов");
    }

    /*
    private String[] jsonArrayToStringArray(JsonArray jsonArray) {
        int arraySize = jsonArray.size();
        String[] stringArray = new String[arraySize];

        for(int i=0; i<arraySize; i++) {
            stringArray[i] = (String) jsonArray.get(i);
        }

        return stringArray;
    };
    */
}
