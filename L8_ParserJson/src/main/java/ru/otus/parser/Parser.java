package ru.otus.parser;

import utils.Types;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Parser implements IParser {
    Map<String, Object> config = new HashMap<String, Object>();
    JsonBuilderFactory factory = Json.createBuilderFactory(config);
    JsonObjectBuilder objectBuilder = factory.createObjectBuilder();


    @Override
    public String jsonFromObject(Object obj) throws IllegalAccessException, ClassNotFoundException {
        config.put("javax.json.stream.JsonGenerator.prettyPrinting", Boolean.valueOf(true));
        return parseObject(obj, objectBuilder).build().toString();
    }

    private JsonObjectBuilder parseObject(Object obj, JsonObjectBuilder objectBuilder) throws IllegalAccessException, ClassNotFoundException {
        Class<?> current = obj.getClass();
        while (current.getSuperclass() != null) {
            Field[] fields = current.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName());
                addToJsonObjectBuilder(objectBuilder, field, obj);
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
                char c = field.getChar(obj);
                value.add(name, Character.valueOf(c).toString());
                break;
            case INT:
                value.add(name, field.getInt(obj));
                break;
            case FLOAT:
                value.add(name, field.getDouble(obj));
                break;
            case LONG:
                value.add(name, field.getLong(obj));
                break;
            case DOUBLE:
                value.add(name, field.getDouble(obj));
                break;
            case ARRAY:
                JsonArrayBuilder jsonArray = getJsonArrayBuilder(field.get(obj));
                value.add(name, jsonArray.build());
                break;
            case OBJECT:
                Object ref = Class.forName(field.getType().getCanonicalName()).cast(field.get(obj));
                Object vals;
                Object keys;
                boolean collection = false;
                if (field.get(obj) instanceof Collection) {
                    vals = ((Collection) ref).toArray();
                    collection = true;
                    JsonArrayBuilder jsonArray1 = getJsonArrayBuilder(vals);
                    value.add(name, jsonArray1.build());
                } else if (field.get(obj) instanceof Map) {
                    vals = ((Map) ref).values().toArray();
                    keys = ((Map) ref).keySet().toArray();
                    collection = true;
                    JsonObjectBuilder objectBuilderMap = getJsonArrayBuilderMap(vals, keys);
                    value.add(name, objectBuilderMap.build());
                }

                if (!collection) {
                    JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
                    value.add(name, parseObject(ref, objectBuilder));
                }
                break;
            default:
                throw new UnsupportedOperationException("Not primetive type");
        }
        field.setAccessible(false);
    }

    private JsonObjectBuilder getJsonArrayBuilderMap(Object vals, Object keys) throws IllegalAccessException, ClassNotFoundException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        int len = Array.getLength(vals);
        for (int i = 0; i < len; i++) {
            Object val = Array.get(vals, i);
            Object key = Array.get(keys, i);
            if (val instanceof Number) {
                if (val instanceof Float || val instanceof Double) {
                    objectBuilder.add(key.toString(), Double.valueOf(val.toString()));
                } else {
                    objectBuilder.add(key.toString(), Long.valueOf(val.toString()));
                }
            } else if (val instanceof String) {
                objectBuilder.add(key.toString(), (String) val);
            } else if (val instanceof Boolean) {
                objectBuilder.add(key.toString(), (Boolean) val);
            } else if (val instanceof Character) {
                objectBuilder.add(key.toString(), val.toString());
            } else {
                JsonObjectBuilder objectBuilder1 = factory.createObjectBuilder();
                objectBuilder.add(key.toString(), parseObject(val, objectBuilder1));
            }
        }
        return objectBuilder;
    }

    private JsonArrayBuilder getJsonArrayBuilder(Object vals) throws IllegalAccessException, ClassNotFoundException {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        System.out.println("ARRAY");
        int len = Array.getLength(vals);
        for (int i = 0; i < len; i++) {
            Object val = Array.get(vals, i);
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
                jsonArray.add(val.toString());
            } else {
                JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
                jsonArray.add(parseObject(val, objectBuilder));
            }

        }
        return jsonArray;
    }


    @Override
    public Object ObjectFromJson(String json) {
        throw new UnsupportedOperationException("метод не готов");
    }

}
