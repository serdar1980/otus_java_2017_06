package ru.otus;

import dbservice.DBService;
import dbservice.HibernateDBService;
import dbservice.JDBCDBService;
import handler.parser.ParserImpl;
import model.AddressDataSet;
import model.DataSet;
import model.PhoneDataSet;
import model.UserDataSet;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class Main {

    public static void main(String... args) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DBService dbService ;
        AddressDataSet address;

        List<PhoneDataSet> testerPhones = new ArrayList<>();
        testerPhones.add(new PhoneDataSet("89080980"));
        testerPhones.add(new PhoneDataSet("84324234"));
        testerPhones.add(new PhoneDataSet("875756756"));

        dbService = new HibernateDBService();
        UserDataSet testerUser = new UserDataSet("tester", 90);
        address= new AddressDataSet("Москва Петровско-разумовская 24 кв 35");
        testerUser.setAddress(address);
        testerUser.setPhones(testerPhones);

        dbService.save(testerUser);

        DataSet testerUserLoad = dbService.read(1L, UserDataSet.class);
        System.out.println(testerUserLoad.toString());

        List<PhoneDataSet> serdarPhones = new ArrayList<>();
        serdarPhones.add(new PhoneDataSet("34534534"));
        serdarPhones.add(new PhoneDataSet("34234234"));
        serdarPhones.add(new PhoneDataSet("4234234234"));


        dbService = new JDBCDBService(new ParserImpl());
        UserDataSet user = new UserDataSet(1L, "serdar", 100);
         address= new AddressDataSet("Москва Петровско-разумовская 24 кв 35");
        user.setAddress(address);
        user.setPhones(serdarPhones);

        System.out.println("Insert user into DB through JDBS DBService");
        dbService.save(user);
        DataSet user1 = dbService.read(1L, UserDataSet.class);
        System.out.println(user.toString());



    }

}

