package db.PG;

import db.DatabaseRepository;
import db.Users;

import java.sql.*;

public class DatabasePG implements DatabaseRepository, Users {
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;
    private static String DATABASE_URL;
    protected static Connection connection;

    public DatabasePG(String DATABASE_USER, String DATABASE_PASSWORD, String DATABASE_URL) throws SQLException {
        DatabasePG.DATABASE_USER = DATABASE_USER;
        DatabasePG.DATABASE_PASSWORD = DATABASE_PASSWORD;
        DatabasePG.DATABASE_URL = DATABASE_URL;
        connection = DriverManager.getConnection(DatabasePG.DATABASE_URL, DatabasePG.DATABASE_USER, DatabasePG.DATABASE_PASSWORD);
    }

    @Override
    public ResultSet get(String tableName) {
        return null;
    }

    @Override
    public void create(String tableName) {

    }

    @Override
    public void update(String tableName) {

    }

    @Override
    public void delete(String tableName) {

    }

    @Override
    public void updateUserInformation(Long user_id, String username, String photo_id) throws SQLException {
        Statement statement = connection.createStatement();
        String response = String.format("""
                   INSERT INTO users (user_id, username, photo_id) VALUES (%s, '%s', '%s')
                   ON CONFLICT (user_id)
                   DO UPDATE SET user_id = excluded.user_id, username = excluded.username, photo_id = excluded.photo_id;
                """, user_id, username, photo_id);
        try {
            statement.executeUpdate(response);
            System.out.printf("Succeful db update by user: %s with username: %s\n", user_id, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getStatusOfWaitingPhoto(Long user_id) {

        return false;
    }

    @Override
    public void setStatusOfWaitingPhoto(Long user_id, boolean newStatus) {

    }


    @Override
    public boolean getStatusOfWaitingName(Long user_id) {

        return false;
    }

    @Override
    public void setStatusOfWaitingName(Long user_id, boolean newStatus) {

    }

}
