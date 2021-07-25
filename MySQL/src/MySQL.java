import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MySQL {

    Connection conn;

    //constructor
    MySQL(String host, String database, String user, String pass) throws Exception {

        //creating connection
        try{
            conn = DriverManager.getConnection((host + "/" + database), "root", "" );
        }catch(SQLException error){
            throw new Exception("error connecting to database: " + error.getMessage());
        }

    }

    boolean checkEntry(String table, Object[][] col_val) throws Exception {

        //handling exceptions of multi dimensional array
        checkException_MultiDimArray(col_val);


        StringBuilder query = new StringBuilder("SELECT * FROM " + table + " WHERE ");

        for (int i = 0, col_valLength = col_val.length; i < col_valLength; i++) {
            Object[] match = col_val[i];
            Object column = match[0];
            Object value = match[1];

            //TODO: '' should only wrap objects of type String not integer
            query.append(column).append(" = '").append(value).append("' ");
            if (i != col_valLength - 1)
                query.append("AND ");

        }

        ResultSet resultSet = conn.createStatement().executeQuery(query.toString());

        return resultSet.next();

    }

    //TODO: make this addSelectedEntry, then add addEntry that doesn't take specified columns
    int addEntry(String table, Object[][] entries) throws Exception{
        //handling exceptions for multi dimensional array
        checkException_MultiDimArray(entries);

        StringBuilder query = new StringBuilder("INSERT INTO " + table + " () VALUES ()");

        for (int i = 0, entryLength = entries.length; i < entryLength; i++) {
            Object[] entry = entries[i];
            Object column = entry[0];
            Object value = entry[1];

            query.insert(query.indexOf(") VALUES"), column + " ");
            query.insert(query.length() - 1, "'" + value + "' ");
            if (i != entryLength - 1){
                query.insert(query.indexOf(") VALUES"), ", ");
                query.insert(query.length()-1, ", ");
            }
        }

//        System.out.println(query);
        return conn.createStatement().executeUpdate(query.toString());
    }

    //TODO: getData without a field parameter which would retrieve all columns (*)
    //getData(String table)
    //getData(String table, String[] columns)
    //getData(String table, String[][] matches)
    HashMap<Integer, HashMap<String, String>> getData(String table, String[] columns, Object[][] matches) throws Exception {
        checkException_MultiDimArray(matches);

        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        for (int i = 0, fieldsLength = columns.length; i < fieldsLength; i++) {
            String field = columns[i];
            queryBuilder.append(field);
            if(i != fieldsLength-1)
                queryBuilder.append(", ");
            else
                queryBuilder.append(" ");
        }

        queryBuilder.append("FROM " + table + " WHERE ");

        for (int i = 0, matchesLength = matches.length; i < matchesLength; i++) {
            Object[] match = matches[i];
            Object column = match[0];
            Object value = match[1];

            queryBuilder.append(column).append(" = '").append(value);

            if(i != matchesLength-1)
                queryBuilder.append("' AND "); //TODO: make logical operator flexible
            else
                queryBuilder.append("' ");
        }

        String query = queryBuilder.toString();

        System.out.println(query);

        ResultSet resultSet = conn.createStatement().executeQuery(query);

        HashMap<Integer, HashMap<String, String>> data = new HashMap<Integer, HashMap<String, String>>();

        while(resultSet.next()){
            HashMap<String, String> rowData = new HashMap<String, String>();
            for (int i = 0, columnsLength = columns.length; i < columnsLength; i++) {
                String column = columns[i];
                rowData.put(column, resultSet.getString(column));
            }
            data.put(resultSet.getRow(), rowData);
        }
        return data;
    }

    void checkException_MultiDimArray(Object[][] m_d_array) throws Exception {
        //handle exceptions: if an multi dim consist of
        // an empty pair,
        // more than two entries for a col - val pair
        // invalid object type (accepts Strings and Integers only for now)

        for (Object[] pair : m_d_array) {
            if (pair.length != 2) {
//                System.out.println("inner array must contain only two values");
                throw new Exception("inner array must contain only two values");
            }
            for (Object value : pair) {
                if (!(value instanceof String) && !(value instanceof Integer)) {
//                    System.out.println("Invalid type passed: " + value + " of type " + value.getClass());
                    throw new Exception("Invalid type passed: " + value + " of type " + value.getClass());
                }
            }
        }
    }

}