package ru.otus.parser.db;

import executor.TExecutor;
import model.UserDataSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import handler.parser.ParserImpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TExecutorTest {

    private static final String URI_CONNECTION = "jdbc:h2:mem:test;USER=sa;PASSWORD=password";
    Connection conn;
    UserDataSet user;
    TExecutor executor;

    @Before
    public void startUp() throws SQLException, ClassNotFoundException {
        DriverManager.registerDriver(new org.h2.Driver());
        conn = DriverManager.getConnection(URI_CONNECTION);
        Statement stm = conn.createStatement();
        stm.execute("create table users (id bigint auto_increment, user_name varchar(256), age int,  primary key (id))");
        System.out.println("Table created");
        stm.execute("insert into users (user_name) values ('tully')");
        System.out.println("User added");
        user = new UserDataSet(2L, "serdar", 37);
        executor = new TExecutor(conn);
    }

    @After
    public void end() throws SQLException {
        conn.close();
    }

    @Test
    public void loadFromDb() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        UserDataSet user1 = executor.load(1, UserDataSet.class, new ParserImpl());
        Assert.assertTrue(user1.getName().equals("tully"));
    }

    @Test
    public void insertToDb() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        executor.save(user, new ParserImpl());
        UserDataSet user1 = executor.load(2, UserDataSet.class, new ParserImpl());
        Assert.assertTrue(user.getName().equals(user1.getName()));
    }

    @Test
    public void updateToDb() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        UserDataSet user1 = executor.load(1, UserDataSet.class, new ParserImpl());
        user1.setName("Gabber");
        executor.save(user1, new ParserImpl());
        UserDataSet user2 = executor.load(1, UserDataSet.class, new ParserImpl());
        Assert.assertTrue(user2.getName().equals(user1.getName()));
    }


}
