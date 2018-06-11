package com.github.manolo8.simplecraft.data.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteMemory implements Database {

    private Connection connection;
    private File dataFolder;

    public String getHost() {
        return "jdbc:sqlite:";
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (connection != null && !connection.isClosed()) return connection;

            connection = DriverManager.getConnection(getHost());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void setDataFolder(File dataFolder) {
        dataFolder.mkdir();
        this.dataFolder = dataFolder;
    }
}
