package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePostgreSQL implements Database {


    public static String DATABASE_USER;
    public static String DATABASE_PASSWORD;
    public static String DATABASE_URL;
    public DatabasePostgreSQL(String DATABASE_USER, String DATABASE_PASSWORD, String DATABASE_URL) throws SQLException {
        DatabasePostgreSQL.DATABASE_USER = DATABASE_USER;
        DatabasePostgreSQL.DATABASE_PASSWORD = DATABASE_PASSWORD;
        DatabasePostgreSQL.DATABASE_URL = DATABASE_URL;
    }
    @Override
    public Connection getConnection() throws SQLException {
        Connection connection =  DriverManager.getConnection(DatabasePostgreSQL.DATABASE_URL, DatabasePostgreSQL.DATABASE_USER, DatabasePostgreSQL.DATABASE_PASSWORD);
        System.out.println("Подключение установлено");
        return connection;
    }

    @Override
    public void getFromDb() {

    }

    @Override
    public void deleteFromDb() {

    }

    @Override
    public void updateToDb(Long user_id, String username, String photo_id) throws SQLException {
        System.out.println("зашло");
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String response = String.format("""
                   INSERT INTO users (user_id, username, photo_id) VALUES (%s, %s, %s)
                   ON CONFLICT (user_id)
                   DO UPDATE SET user_id = excluded.user_id, username = excluded.username, photo_id = excluded.photo_id;
                """, user_id, username, photo_id);
        connection.close();
        try {
            statement.executeUpdate(response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
