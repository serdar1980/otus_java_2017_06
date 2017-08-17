package ru.otus.parser;

public interface IParser {

    String jsonFromObject(Object obj) throws IllegalAccessException, ClassNotFoundException;

    Object ObjectFromJson(String json);
}
