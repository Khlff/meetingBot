package db;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface Users{
    void updateUserInformation(Long user_id, String username, String photo_id) throws SQLException;
    boolean getStatusOfWaitingPhoto(Long user_id);
    void setStatusOfWaitingPhoto(Long user_id, boolean newStatus);
    boolean getStatusOfWaitingName(Long user_id);
    void setStatusOfWaitingName(Long user_id, boolean newStatus);
}
