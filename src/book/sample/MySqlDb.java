package book.sample;

import java.sql.Connection;
import java.sql.DriverManager;
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

    public void deleteRecordById(String tableName, String primaryKey, Object recordId) throws SQLException {

        String recordToDelete;

        if (recordId instanceof String) {
            recordToDelete = "\'" + recordId + "\'";
        } else {
            recordToDelete = "" + recordId + "";
        }

        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + "= " + recordToDelete;
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);

    }

    public static void main(String[] args) throws Exception {
        MySqlDb db = new MySqlDb();

        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book2",
                "root",
                "admin");

        db.deleteRecordById("author", "author_name", "jigo patel");
        List<Map<String, Object>> records = db.findAllRecords("author");

        for (Map record : records) {
            System.out.println(record);
        }

        db.closeConnection();
    }

}
