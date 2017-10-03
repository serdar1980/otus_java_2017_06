package handler.parser;

import java.sql.SQLException;
import java.util.Map;

public interface Iparser {
    String getTable(Object objectToParse) throws SQLException;

    String getFileds(Object objectToParse) throws SQLException;

    String getQueryForInsert(Object objectToParse) throws SQLException;

    String getQueryForUpdate(Object objectToParse) throws SQLException;

    Map<Object, String> getOneToOneRelationQueryForInsert(Object objectToParse);

    Map<String, String> getManyToOneRelationQueryForInsert(Object objectToParse);

    Map<String, String> getOneToOneRelationQueryForUpdate(Object objectToParse);

    Map<String, String> getManyToOneRelationQueryForUpdate(Object objectToParse);

    void setValue(String val, String columnName, Object obj) throws SQLException;
}
