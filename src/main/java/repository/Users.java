package repository;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Users extends BaseTable implements UsersOperation {

    Users(String tableName) throws SQLException {
        super(tableName);
    }

    @Override
    public void updateUserInformation(Long user_id, String username, String photo_id) throws SQLException {

        super.executeSqlStatement(String.format("""
                   INSERT INTO users (user_id, username, photo_id) VALUES (%s, '%s', '%s')
                   ON CONFLICT (user_id)
                   DO UPDATE SET user_id = excluded.user_id, username = excluded.username, photo_id = excluded.photo_id;
                """, user_id, username, photo_id), "Информация обновлена");
    }

    @Override
    public boolean getStatusOfWaitingUpdate(Long user_id) throws SQLException {
        ResultSet answer = super.executeQuerySqlStatement(String.format("""
                SELECT status_of_waiting_update FROM users where user_id = %s;
                """, user_id), null);
        answer.next();
        return answer.getBoolean("status_of_waiting_update");
    }

    @Override
    public void setStatusOfWaitingUpdate(Long user_id, boolean newStatus) throws SQLException {
        super.executeSqlStatement(String.format("""
                INSERT INTO users (user_id, status_of_waiting_update) VALUES (%s, %s)
                ON CONFLICT (user_id)
                DO UPDATE SET user_id = excluded.user_id, status_of_waiting_update = excluded.status_of_waiting_update;
                """, user_id, newStatus), null);
    }

    @Override
    public boolean getStatusOfWaitingRate(Long user_id) throws SQLException {
        ResultSet answer = super.executeQuerySqlStatement(String.format("""
                   SELECT status_of_waiting_rate FROM users where user_id = %s
                """, user_id), null);
        answer.next();
        return answer.getBoolean("status_of_waiting_rate");
    }

    @Override
    public void setStatusOfWaitingRate(Long user_id, boolean newStatus) throws SQLException {
        super.executeSqlStatement(String.format("""
                INSERT INTO users (user_id, status_of_waiting_rate) VALUES (%s, %s)
                ON CONFLICT (user_id)
                DO UPDATE SET user_id = excluded.user_id, status_of_waiting_rate = excluded.status_of_waiting_rate;
                """, user_id, newStatus), null);
    }
}
