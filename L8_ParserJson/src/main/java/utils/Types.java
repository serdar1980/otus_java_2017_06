package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Types {
    BYTE,
    BOOLEAN,
    SHORT,
    CHAR,
    INT,
    FLOAT,
    LONG,
    DOUBLE,
    ARRAY,
    OBJECT,
    STRING;

    private static final String ALL_TYPES_STRING = Arrays.toString(Types.values());

    private static final Map<Object, Types> map = Arrays.stream(Types.values())
            .collect(Collectors.toMap(
                    e -> e.name(), // Это ключ в мапе, имя класса
                    Function.identity()));

    public static Types getType(Class<?> clazz) {
        String className = clazz.getSimpleName().toUpperCase();
        map.get(clazz.getSimpleName());
        if (ALL_TYPES_STRING.contains(className)) {
            return Types.valueOf(className);
        } else {
            return (className.contains("[]")) ? Types.ARRAY : Types.OBJECT;
        }
    }
}