package handler.parser;

import java.sql.SQLException;

public interface Iparser {
    String getTable(Object objectToParse) throws SQLException;

    String getFileds(Object objectToParse) throws SQLException;

    String getQueryForInsert(Object objectToParse) throws SQLException;

    String getQueryForUpdate(Object objectToParse) throws SQLException;

    void setValue(String val, String columnName, Object obj) throws SQLException;
}
