package parser;

import com.sun.deploy.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ParserImpl implements Iparser {
    private Object objectToParse;
    private String SEPARATOR = ", ";

    public ParserImpl() {

    }

    @Override
    public String getTable(Object objectToParse) throws SQLException {
        Table table = objectToParse.getClass().getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        } else {
            throw new SQLException("Entity : " + objectToParse.getClass().getSimpleName() + " does not have Table annotation");
        }

    }

    @Override
    public String getFileds(Object objectToParse) throws SQLException {
        List<String> fileds = new ArrayList<>();
        for (Method method : objectToParse.getClass().getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation.annotationType().equals(Column.class)) {
                    Column column = (Column) annotation;
                    fileds.add(column.name());
                }
            }
        }
        if (fileds.size() > 0) {
            return StringUtils.join(fileds, SEPARATOR);
        } else {
            throw new SQLException("Entity : " + objectToParse.getClass().getSimpleName() + " does not have Column annotation");
        }
    }

    @Override
    public String getQueryForInsert(Object objectToParse) throws SQLException {
        StringBuilder query = new StringBuilder("Insert into ")
                .append(getTable(objectToParse))
                .append("(")
                .append(getFileds(objectToParse))
                .append(") Values(");
        String separator = "";
        try {
            for (Method method : objectToParse.getClass().getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation.annotationType().equals(Column.class)) {
                        Object ret = method.invoke(objectToParse, new Object[]{});
                        query.append(separator);
                        query.append("'");
                        query.append(ret);
                        query.append("'");
                        separator = ", ";
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new SQLException(ex.getMessage());
        }
        query.append(")");
        return query.toString();
    }

    @Override
    public String getQueryForUpdate(Object objectToParse) throws SQLException {
        StringBuilder query = new StringBuilder("Update ")
                .append(getTable(objectToParse))
                .append(" SET ");
        String separator = "";
        String where = "";
        try {
            for (Method method : objectToParse.getClass().getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation.annotationType().equals(Column.class)) {
                        Object ret = method.invoke(objectToParse, new Object[]{});
                        Column column = (Column) annotation;
                        if (column.name().toLowerCase().equals("id")) {
                            where = " Where id = '" + ret + "'";
                        } else {
                            query.append(separator);

                            query.append(column.name());
                            query.append(" = ");
                            query.append("'");
                            query.append(ret);
                            query.append("'");
                            separator = ", ";
                        }
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new SQLException(ex.getMessage());
        }
        query.append(where);
        return query.toString();
    }

    @Override
    public void setValue(String val, String columnName, Object obj) throws SQLException {
        try {
            for (Method method : obj.getClass().getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation.annotationType().equals(Column.class)) {
                        Column column = (Column) annotation;
                        if (column.name().toUpperCase().equals(columnName)) {
                            String setter = method.getName().replace("get", "set");
                            Method setMethod = obj.getClass().getMethod(setter, new Class[]{method.getReturnType()});
                            setValueToObject(setMethod, val, obj);
                        }
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    private void setValueToObject(Method m, String res, Object obj) throws InvocationTargetException, IllegalAccessException {
        m.setAccessible(true);
        Class<?>[] types = m.getParameterTypes();
        String TYPE = types[0].getName();
        Integer val;
        switch (TYPE) {
            case "java.lang.String":
                m.invoke(obj, new Object[]{res});
                break;
            case "int":
                m.invoke(obj, new Object[]{(res == null) ? 0 : Integer.valueOf(res)});
                break;
            case "java.lang.Integer":
                m.invoke(obj, new Object[]{(res == null) ? null : Integer.valueOf(res)});
                break;
            case "long":
                m.invoke(obj, new Object[]{(res == null) ? 0 : Long.valueOf(res)});
                break;
            case "java.lang.Long":
                m.invoke(obj, new Object[]{(res == null) ? null : Long.valueOf(res)});
                break;
            case "double":
                m.invoke(obj, new Object[]{(res == null) ? 0 : Double.valueOf(res)});
                break;
            case "java.lang.Double":
                m.invoke(obj, new Object[]{(res == null) ? null : Double.valueOf(res)});
                break;
            case "float":
                m.invoke(obj, new Object[]{(res == null) ? 0 : Float.valueOf(res)});
                break;
            case "java.lang.Float":
                m.invoke(obj, new Object[]{(res == null) ? null : Float.valueOf(res)});
                break;
            case "byte":
                m.invoke(obj, new Object[]{(res == null) ? 0 : Byte.valueOf(res)});
                break;
            case "java.lang.Byte":
                m.invoke(obj, new Object[]{(res == null) ? null : Byte.valueOf(res)});
                break;
            case "boolean":
                m.invoke(obj, new Object[]{(res == null) ? false : Boolean.valueOf(res)});
                break;
            case "java.lang.Boolean":
                m.invoke(obj, new Object[]{(res == null) ? null : Boolean.valueOf(res)});
                break;
            case "short":
                m.invoke(obj, new Object[]{(res == null) ? 0 : Short.valueOf(res)});
                break;
            case "java.lang.Short":
                m.invoke(obj, new Object[]{(res == null) ? null : Short.valueOf(res)});
                break;
            default:
                throw new UnsupportedOperationException(TYPE + " needs to write parse");
        }
        m.setAccessible(false);
    }

}