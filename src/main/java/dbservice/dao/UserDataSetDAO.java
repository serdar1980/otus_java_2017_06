package dbservice.dao;

import dbservice.DBService;
import model.DataSet;
import model.UserDataSet;

import java.util.List;

public class UserDataSetDAO extends DataSet{
        private DBService dbService;

        public UserDataSetDAO(DBService dbService) {
            this.dbService = dbService;
        }

        public void save(UserDataSet dataSet) {
            dbService.save(dataSet);
        }

        public UserDataSet read(long id) {
            return (UserDataSet)dbService.read(id, UserDataSet.class);
        }

        public UserDataSet readByName(String name) {
            return (UserDataSet)dbService.readByName(name, UserDataSet.class);
        }

        public List<UserDataSet> readAll() {
            List<? extends Object> list =dbService.readAll(UserDataSet.class);
            return (List<UserDataSet>)list;
        }
}
