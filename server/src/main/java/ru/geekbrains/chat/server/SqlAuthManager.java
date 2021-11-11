package ru.geekbrains.chat.server;

import java.sql.*;

public class SqlAuthManager implements AuthManager {

    private static Connection connection;
    private static Statement stmt;


    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:dbmain.db");
        stmt = connection.createStatement();
    }

    public void disconnect(){
        try {
            if (stmt != null){
                stmt.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            if (connection != null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        String result = "";
        try {
            ResultSet rs = stmt.executeQuery("SELECT name FROM users WHERE login like " + "'" + login + "'" + "AND pass like " + "'" + password + "'");
            while (rs.next()){
              result = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }
}
