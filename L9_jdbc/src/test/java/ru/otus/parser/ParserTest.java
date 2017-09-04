package ru.otus.parser;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import model.UserDataSet;
import org.junit.Before;
import org.junit.Test;
import parser.Iparser;
import parser.ParserImpl;

public class ParserTest {
    private final static String INSERT_STR = "Insert into users(user_name, age, id) Values('Вася', '30', '100')";
    private final static String UPDATE_STR = "Update users SET user_name = 'Вася', age = '30' Where id = '100'";
    UserDataSet user;
    Iparser parser;

    @Before
    public void startUp() {
        user = new UserDataSet(100, "Вася", 30);
        parser = new ParserImpl();
    }

    @Test
    public void getTableShouldReturnString() throws SQLException {
        assertTrue(parser.getTable(user).equals("users"));
    }

    @Test(expected = SQLException.class)
    public void getTableShouldReturnException() throws SQLException {
        new ParserImpl().getTable(new Object());
    }

    @Test
    public void getFieldsShouldReturnStringWithCommonSeparator() throws SQLException {
        String[] parts = parser.getFileds(user).split(", ");
        assertTrue(parts.length == 3);
    }

    @Test(expected = SQLException.class)
    public void getFieldsShouldReturnException() throws SQLException {
        new ParserImpl().getFileds(new Object());
    }

    @Test
    public void getQueryForInsert() throws SQLException {
        String str = new ParserImpl().getQueryForInsert(user);
        assertTrue(str.equals(INSERT_STR));
    }

    @Test
    public void getQueryForUpdate() throws SQLException {
        String str = new ParserImpl().getQueryForUpdate(user);
        assertTrue(str.equals(UPDATE_STR));
    }

    @Test(expected = SQLException.class)
    public void getQueryForInsertWithException() throws SQLException {
        String str = new ParserImpl().getQueryForInsert(new Object());
    }

    @Test(expected = SQLException.class)
    public void getQueryForUpdateWithException() throws SQLException {
        String str = new ParserImpl().getQueryForUpdate(new Object());
    }
}
