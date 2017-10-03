package ru.otus.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {


    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://" +       //db type
                    "localhost:" +               //host name
                    "3306/" +                    //port
                    "db_example?" +              //db name
                    "useSSL=false&" +            //do not use ssl
                    "user=project&" +              //login
                    "password=project";            //password

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String status() {
        try (Connection connection = getConnection()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Connected to: ").append(connection.getMetaData().getURL());
            sb.append("DB name: " ).append( connection.getMetaData().getDatabaseProductName());
            sb.append("DB version: " ).append(  connection.getMetaData().getDatabaseProductVersion());
            sb.append("Driver: " ).append(  connection.getMetaData().getDriverName());
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
