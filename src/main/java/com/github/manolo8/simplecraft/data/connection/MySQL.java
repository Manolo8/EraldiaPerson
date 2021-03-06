package com.github.manolo8.simplecraft.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL implements Database {
    private String host;
    private String username;
    private String password;
    private String dataBase;
    private Connection connection;

    public String getHost() {
        return "jdbc:mysql://"
                + host
                + "/" + dataBase
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Connection getConnection() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            if (connection != null && !connection.isClosed()) return connection;
            connection = DriverManager.getConnection(getHost(), getUsername(), getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
