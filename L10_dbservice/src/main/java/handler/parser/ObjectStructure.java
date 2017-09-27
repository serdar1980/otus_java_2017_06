package handler.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serdar on 04.09.2017.
 */
public class ObjectStructure {

  private String tableName;
  private List<String> Fields = new ArrayList<>();

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<String> getFields() {
    return Fields;
  }

  public void setFields(List<String> fields) {
    Fields = fields;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ObjectStructure)) {
      return false;
    }

    ObjectStructure that = (ObjectStructure) o;

    if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) {
      return false;
    }
    return Fields != null ? Fields.equals(that.Fields) : that.Fields == null;

  }

  @Override
  public int hashCode() {
    int result = tableName != null ? tableName.hashCode() : 0;
    result = 31 * result + (Fields != null ? Fields.hashCode() : 0);
    return result;
  }
}

