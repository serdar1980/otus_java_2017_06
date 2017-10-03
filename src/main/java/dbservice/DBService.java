package dbservice;

import model.DataSet;
import model.UserDataSet;

import java.util.List;

public interface DBService {
    String getLocalStatus();

    void save(DataSet dataSet);

    <T extends DataSet> DataSet read(long id , Class clazz);

    DataSet readByName(String name, Class clazz);

    List<? extends DataSet> readAll(Class clazz);

    void shutdown();
}
