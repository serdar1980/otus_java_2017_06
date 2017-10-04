package dbservice;

import executor.TExecutor;
import handler.parser.Iparser;
import model.DataSet;
import ru.otus.utils.ConnectionHelper;

import java.sql.Connection;
import java.util.List;

public class JDBCDBService implements DBService {
    TExecutor executor;
    Iparser parser;

    public JDBCDBService(Iparser parser){
        Connection conn = ConnectionHelper.getConnection();
        executor = new TExecutor(conn);
        this.parser =  parser;
    }
    @Override
    public String getLocalStatus() {
        return ConnectionHelper.status();
    }

    @Override
    public void save(DataSet dataSet) {
        try {

            executor.save(dataSet, parser);
        }catch (Exception ex){
            //TODO log exception and throw;
            ex.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> DataSet read(long id, Class clazz) {
        try {
            return executor.load(id, clazz, parser);
        }catch (Exception ex){
            //TODO log exception and throw;
            return null;
        }
    }

    @Override
    public DataSet readByName(String name, Class clazz) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public List<? extends DataSet> readAll(Class clazz) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void shutdown() {
    }
}
