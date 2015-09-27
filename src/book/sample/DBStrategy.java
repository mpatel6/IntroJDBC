/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.sample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ankita
 */
public interface DBStrategy {

    void closeConnection() throws SQLException;

    int deleteRecordById(String tableName, String recordField, Object recordValue) throws SQLException;

    List<Map<String, Object>> findAllRecords(String tableName) throws SQLException;

    int insertRecord(String tableName, List<String> recordFields, List<Object> recordValues) throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws Exception;

    int updateRecord(String tableName, String recordWhereField, Object recordWhereValue, List<String> recordFields, List<Object> recordValues) throws SQLException;
    
}
