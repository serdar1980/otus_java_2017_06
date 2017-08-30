package executor;

import model.DataSet;
import parser.Iparser;

import java.sql.*;

public class TExecutor {
    private final Connection connection;

    public TExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T dataSet, Iparser parser) throws SQLException {
        String updateQuery = parser.getQueryForUpdate(dataSet);
        String insertQuery = "";
        Statement stmt = connection.createStatement();
        int updateCount = stmt.executeUpdate(updateQuery);
        if (updateCount == 0) {
            insertQuery = parser.getQueryForInsert(dataSet);
            stmt.execute(insertQuery);
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz, Iparser parser) throws SQLException {
        if (id < 0) {
            return null;
        }
        try {
            T dateSet = clazz.newInstance();
            String queryTpl = "select " + parser.getFileds(dateSet) + " from " + parser.getTable(dateSet) + " where id ='" + id + "'";

            String query = queryTpl;
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            result.next();
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String name = rsmd.getColumnName(i);
                String res = result.getString(name);
                parser.setValue(res, name, dateSet);
            }
            result.close();
            stmt.close();
            return dateSet;
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new SQLException(ex.getMessage());
        }
    }


}
