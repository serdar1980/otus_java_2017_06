package ru.otus;

import executor.TExecutor;
import model.DataSet;
import model.UserDataSet;
import parser.ParserImpl;
import ru.otus.utils.ConnectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

class Main {

    public static void main(String... args) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection conn = ConnectionHelper.getConnection();
        TExecutor executor = new TExecutor(conn);
        DataSet user = new UserDataSet(1, "serdar", 100);
        System.out.println("Insert user into DB");
        executor.save(user, new ParserImpl());
        DataSet user1 = executor.load(1, UserDataSet.class, new ParserImpl());
        System.out.println(user.toString());
    }

}

