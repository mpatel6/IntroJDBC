package book.sample;

import java.sql.*;
import java.util.*;

public class MySqlDb {

    private Connection conn;

    public void openConnection(String driverClass, String url, String userName, String password) throws Exception {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

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

    public int insertRecord(String tableName, List<String> recordFields, List<Object> recordValues) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = buildInsertStatement(conn, tableName, recordFields);
        for (int i = 1; i <= recordFields.size(); i++) {
            pstmt.setObject(i, recordValues.get(i - 1));
        }
        return pstmt.executeUpdate();
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
        int updateQueryInt = db.insertRecord("author", recordFields, recordValues);
        System.out.println("Number of Records Created=" + updateQueryInt);

        List<Map<String, Object>> records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }

        db.closeConnection();
    }

}
