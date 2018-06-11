package com.github.manolo8.simplecraft.data.connection;

import org.bukkit.plugin.Plugin;

import java.sql.SQLException;

public class DatabaseBuilder {

    private Database database;

    public Database getDatabase() {
        return database;
    }

    public DatabaseBuilder build(Plugin plugin) {
        SQLite SQLite = new SQLite();
        SQLite.setDataFolder(plugin.getDataFolder());
        this.database = SQLite;
        SQLite.getConnection();

        return this;
    }

    public void closeConnection() {
        try {
            database.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
