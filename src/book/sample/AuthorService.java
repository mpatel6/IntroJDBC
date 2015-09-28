/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.sample;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ankita
 */
public class AuthorService {

    private AuthorDaoStrategy dao;

    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;
    }

    public List<Author> findAllAuthors() throws Exception {
        return dao.findAllAuthors();
    }

    public int deleteRecordById(String recordField, Object recordValue) throws Exception {
        return dao.deleteRecordById(recordField, recordValue);
    }

    public int insertRecord(Author author) throws Exception {
        return dao.insertRecord(author);
    }

    public int updateRecord(Author author) throws Exception {
        return dao.updateRecord(author);
    }

    public static void main(String[] args) throws Exception {
        AuthorService authorService = new AuthorService(new AuthorDAO(new MySqlDb(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book2", "root", "admin"));

       //authorService.deleteRecordById("author_id", "18");
        List<Object> recordValues = new ArrayList<>();
        recordValues.add("Aditi Shah");
        recordValues.add("2007-05-06");

        List<String> recordFields = new ArrayList<>();
        recordFields.add("author_name");
        recordFields.add("date_created");

           authorService.insertRecord(new Author("Mack Patel", new Date()));
     //   authorService.updateRecord("author_id", "22", recordFields, recordValues);
        List<Author> authorList = new ArrayList<>();
        authorList = authorService.findAllAuthors();
        for (Author author : authorList) {
            System.out.println(author);
        }

    }

}
