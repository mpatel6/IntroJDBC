package book.sample;

import java.sql.*;
import java.util.*;

public class MySqlDb implements DBStrategy {

    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url, String userName, String password) throws Exception {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    @Override
    public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException {
        List<Map<String, Object>> records = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int colCount = metaData.getColumnCount();
        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            records.add(record);
        }
        return records;
    }

    @Override
    public int deleteRecordById(String tableName, String recordField, Object recordValue) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = buildDeleteStatement(conn, tableName, recordField);
        if (recordField != null) {
            if (recordValue instanceof String) {
                pstmt.setString(1, recordValue.toString());
            } else {
                pstmt.setObject(1, recordValue);
            }
        }
        return pstmt.executeUpdate();
    }

    private PreparedStatement buildDeleteStatement(Connection connect, String tableName, String recordField) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tableName);
        if (recordField != null) {
            sql.append(" WHERE ").append(recordField).append(" = ?");
        }
        return connect.prepareStatement(sql.toString());
    }

    @Override
    public int updateRecord(String tableName, String recordWhereField, Object recordWhereValue, List<String> recordFields, List<Object> recordValues) throws SQLException {
        //update author Set author_name = "Rosmund Pike", date_created = "1998-05-05" Where author_id = "13";

        PreparedStatement pstmt = null;
        pstmt = buildUpdateStatement(conn, tableName, recordWhereField, recordFields);
        for (int i = 1; i <= recordFields.size(); i++) {
            pstmt.setObject(i, recordValues.get(i - 1));
            if (i == recordFields.size()) {
                pstmt.setObject(recordFields.size() + 1, recordWhereValue);
            }
        }

        return pstmt.executeUpdate();
    }

    private PreparedStatement buildUpdateStatement(Connection connect, String tableName, String recordWhereField, List<String> recordFields) throws SQLException {
        StringBuilder sql = new StringBuilder("Update ");
        sql.append(tableName).append(" SET ");

        for (int i = 0; i < recordFields.size(); i++) {
            sql.append(recordFields.get(i)).append(" = ").append("?");
            if (i < recordFields.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(" WHERE ").append(recordWhereField).append(" = ").append("?").append(";");
        //System.out.println(sql.toString());
        return connect.prepareStatement(sql.toString());
    }

    @Override
    public int insertRecord(String tableName, List<String> recordFields, List<Object> recordValues) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = buildInsertStatement(conn, tableName, recordFields);
        for (int i = 1; i <= recordFields.size(); i++) {
            pstmt.setObject(i, recordValues.get(i - 1));
        }
        return pstmt.executeUpdate();
        //        StringBuilder sql = new StringBuilder("Update ");
//        sql.append(tableName).append(" SET ");
//        
//        for(int i=0; i<recordFields.size();i++){
//            sql.append(recordFields.get(i)).append(" = \"").append(recordValues.get(i)).append("\" ");
//            if(i< recordFields.size()-1){
//                sql.append(",");
//            }            
//        }
//        
//        sql.append(" WHERE ").append(recordWhereField).append(" = \"").append(recordWhereValue).append("\";");  
//        Statement stmt = conn.createStatement();
//        
//        
//        System.out.println(sql.toString());
//        return stmt.executeUpdate(sql.toString());
    }

    private PreparedStatement buildInsertStatement(Connection connect, String tableName, List<String> recordFields) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName).append("(");
        for (int i = 0; i < recordFields.size(); i++) {
            sql.append(recordFields.get(i));
            if (i < recordFields.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(") VALUES (");
        for (int i = 0; i < recordFields.size(); i++) {
            sql.append("?");
            if (i < recordFields.size() - 1) {
                sql.append(",");
            }

        }
        sql.append(");");
        //System.out.println("INSERRT SQL STATEMENT" + sql);
        return connect.prepareStatement(sql.toString());
    }

    public static void main(String[] args) throws Exception {
        MySqlDb db = new MySqlDb();

        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book2",
                "root",
                "admin");

        List<Object> recordValues = new ArrayList<>();
        recordValues.add("David Samson1");
        recordValues.add("2015-05-06");

        List<String> recordFields = new ArrayList<>();
        recordFields.add("author_name");
        recordFields.add("date_created");
//        int updateQueryInt = db.insertRecord("author", recordFields, recordValues);
//        System.out.println("Number of Records Created=" + updateQueryInt);

        int udateRecordInt = db.updateRecord("author", "author_id", "12", recordFields, recordValues);
        System.out.println("Number of Records updated=" + udateRecordInt);

        List<Map<String, Object>> records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }

        db.closeConnection();
    }

}
