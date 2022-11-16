package db;


import java.sql.*;

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
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DatabasePostgreSQL.DATABASE_URL, DatabasePostgreSQL.DATABASE_USER, DatabasePostgreSQL.DATABASE_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getFromDb() {

    }

    @Override
    public ResultSet getColumn() {
        String SQL = "SELECT * FROM users";
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(SQL);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public void deleteFromDb() {

    }

    @Override
    public void updateToDb(Long user_id, String username, String photo_id) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String response = String.format("""
                   INSERT INTO users (user_id, username, photo_id) VALUES (%s, '%s', '%s')
                   ON CONFLICT (user_id)
                   DO UPDATE SET user_id = excluded.user_id, username = excluded.username, photo_id = excluded.photo_id;
                """, user_id, username, photo_id);
        try {
            statement.executeUpdate(response);
            System.out.printf("Successful db update by user: %s with username: %s\n", user_id, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
