package executor;

import model.DataSet;
import handler.parser.Iparser;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TExecutor {
    private final Connection connection;

    public TExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T dataSet, Iparser parser) throws SQLException {
        connection.setAutoCommit(false);
        String updateQuery = parser.getQueryForUpdate(dataSet);
        String insertQuery = "";
        Statement stmt = connection.createStatement();
        int updateCount = stmt.executeUpdate(updateQuery);
        if (updateCount == 0) {
            Map<String , Long> IdForOneToOne = new HashMap<>();
            String generatedColumns[] = { "ID" };
            Map<Object, String> insertsBeforGeneralSql = parser.getOneToOneRelationQueryForInsert(dataSet);
            for (Object insertStrFiled : insertsBeforGeneralSql.keySet()) {
                PreparedStatement stntTemp =
                        connection.prepareStatement(insertsBeforGeneralSql.get(insertStrFiled), generatedColumns);
                stntTemp.execute();
                ResultSet rs = stntTemp.getGeneratedKeys();
                ((DataSet)insertStrFiled).setId(getId(rs));
                rs.close();
            }
            insertQuery = parser.getQueryForInsert(dataSet);
            PreparedStatement stmtInsert = connection.prepareStatement(insertQuery, generatedColumns);
            stmtInsert.executeUpdate();
            ResultSet rs = stmtInsert.getGeneratedKeys();
        }
        connection.commit();
    }

    private Long getId(ResultSet rs) throws SQLException {
        Long id ;
        try {
            if (rs.next()) {
                id = rs.getLong(1);
                System.out.println("Inserted ID -" + id); // display inserted record
            }
        }finally {
            id  =-1L;
        }

        return id;
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
