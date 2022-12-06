package db;

import java.sql.ResultSet;

public interface DatabaseRepository extends Users{

    ResultSet get(String tableName);

    void create(String tableName);

    void update(String tableName);

    void delete(String tableName);
}