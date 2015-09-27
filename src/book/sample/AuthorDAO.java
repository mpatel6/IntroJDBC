/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ankita
 */
public class AuthorDAO {
    
    private DBStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public AuthorDAO(DBStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    public int deleteRecordById(String tableName, String recordField, Object recordValue) throws Exception{
        db.openConnection(driverClass, url, userName, password);
        int noOfRecordDeleted = db.deleteRecordById(tableName, recordField, recordValue);
        db.closeConnection();
        return noOfRecordDeleted;
    }
    
    public int insertRecord(String tableName, List<String> recordFields, List<Object> recordValues) throws Exception{
        db.openConnection(driverClass, url, userName, password);
        int noOfRecordsInserted = db.insertRecord(tableName, recordFields, recordValues);
        db.closeConnection();
        return noOfRecordsInserted;
    }
    
    public int updateRecord(String tableName, String recordWhereField, Object recordWhereValue, List<String> recordFields, List<Object> recordValues) throws Exception{
      db.openConnection(driverClass, url, userName, password);
        int noOfRecordsUpdated = db.updateRecord(tableName, recordWhereField,recordWhereValue,recordFields, recordValues);
        db.closeConnection();
        return noOfRecordsUpdated;  
    }
    
    
    
    public static void main(String[] args) throws Exception {
        AuthorDAO dao = new AuthorDAO(new MySqlDb(),"com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book2",
                "root",
                "admin");
        
//        int noOfRecordsDeleted = dao.deleteRecordById("author","author_id","11");
//        System.out.println("Number of Records Deleted = " + noOfRecordsDeleted);

        List<Object> recordValues = new ArrayList<>();
        recordValues.add("David Shah");
        recordValues.add("2015-05-06");

        List<String> recordFields = new ArrayList<>();
        recordFields.add("author_name");
        recordFields.add("date_created");

//        int noOfRecordsInserted = dao.insertRecord("author",recordFields,recordValues);
//        System.out.println("Number of Records Added = " + noOfRecordsInserted);
        
        int noOfRecordsUpdated = dao.updateRecord("author","author_id","12",recordFields,recordValues);
        System.out.println("Number of Records Added = " + noOfRecordsUpdated);

//        List<Map<String, Object>> records = db.findAllRecords("author");
//        for (Map record : records) {
//            System.out.println(record);
//        }

    }
    
}
