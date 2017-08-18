package utils;

import javax.print.DocFlavor;
import java.util.Arrays;

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

    public static Types getType(Class<?> clazz) {
        String className = clazz.getSimpleName().toUpperCase();
        if (ALL_TYPES_STRING.contains(className)) {
            return Types.valueOf(className);
        } else {
            return (className.contains("[]")) ? Types.ARRAY : Types.OBJECT;
        }
    }
}