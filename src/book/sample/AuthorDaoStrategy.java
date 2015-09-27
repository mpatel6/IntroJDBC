/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.sample;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Ankita
 */
public interface AuthorDaoStrategy {

    int deleteRecordById(String recordField, Object recordValue) throws Exception;

    List<Author> findAllAuthors() throws SQLException, Exception;

    int insertRecord(List<String> recordFields, List<Object> recordValues) throws Exception;

    int updateRecord(String recordWhereField, Object recordWhereValue, List<String> recordFields, List<Object> recordValues) throws Exception;
    
}
