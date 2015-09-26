package book.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //Generics used but not available for older versions
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

        String recordToDelete;
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

    public static void main(String[] args) throws Exception {
        MySqlDb db = new MySqlDb();

        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book2",
                "root",
                "admin");

        int numberOfRecordsDeleted = db.deleteRecordById("author", "author_name", "david Patel");
        System.out.println("Number of Records Deleted=" + numberOfRecordsDeleted);
        List<Map<String, Object>> records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }

        db.closeConnection();
    }

}
