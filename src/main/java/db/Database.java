package db;

import java.sql.SQLException;

public interface Database {
    void getFromDb();
    void deleteFromDb();
    void updateToDb(Long user_id, String username,String photo_id) throws SQLException;
}
