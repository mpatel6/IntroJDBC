/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.sample;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ankita
 */
public class AuthorDAO implements AuthorDaoStrategy {
    
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
    
    @Override
    public int deleteRecordById(String recordField, Object recordValue) throws Exception{
        db.openConnection(driverClass, url, userName, password);
        int noOfRecordDeleted = db.deleteRecordById("author", recordField, recordValue);
        db.closeConnection();
        return noOfRecordDeleted;
    }
    
    @Override
    public int insertRecord( List<String> recordFields, List<Object> recordValues) throws Exception{
        db.openConnection(driverClass, url, userName, password);
        int noOfRecordsInserted = db.insertRecord("author", recordFields, recordValues);
        db.closeConnection();
        return noOfRecordsInserted;
    }
    
    @Override
    public int updateRecord(String recordWhereField, Object recordWhereValue, List<String> recordFields, List<Object> recordValues) throws Exception{
      db.openConnection(driverClass, url, userName, password);
        int noOfRecordsUpdated = db.updateRecord("author", recordWhereField,recordWhereValue,recordFields, recordValues);
        db.closeConnection();
        return noOfRecordsUpdated;  
    }
    
 
    @Override
    public List<Author> findAllAuthors() throws SQLException, Exception{
       db.openConnection(driverClass, url, userName, password);
       List<Author> authorList = new ArrayList<>();
       
       List<Map<String, Object>> rawData = new ArrayList<>();
       rawData = db.findAllRecords("author");
       
       for(Map rawRec:rawData){
           Author author = new Author();
            Object obj = rawRec.get("author_id");
            author.setAuthorId(Integer.parseInt(obj.toString()));
            
            String authorName = rawRec.get("author_name") == null ? "" : rawRec.get("author_name").toString();
            author.setAuthorName(authorName);
            
            obj = rawRec.get("date_created");
            Date dateCreated = (Date)rawRec.get("date_created");
            author.setDateCreated(dateCreated);
            authorList.add(author);
           
       }
       
       
         db.closeConnection();      
       
       
       return authorList;
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
        
//        int noOfRecordsUpdated = dao.updateRecord("author","author_id","12",recordFields,recordValues);
//        System.out.println("Number of Records Added = " + noOfRecordsUpdated);

        
        List<Author> authorList = new ArrayList<>();
        authorList = dao.findAllAuthors();
        
        for (Author author : authorList) {
            System.out.println(author);
        }

    }
    
}
