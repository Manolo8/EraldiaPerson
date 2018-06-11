package com.github.manolo8.eraldiaperson;

import com.github.manolo8.eraldiaperson.user.UserRepository;
import com.github.manolo8.simplecraft.data.cache.CacheManager;
import com.github.manolo8.simplecraft.data.connection.Database;
import com.github.manolo8.simplecraft.data.connection.DatabaseBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class EraldiaPerson extends JavaPlugin {

    private DatabaseBuilder databaseBuilder;
    private CacheManager cacheManager;

    private UserRepository userRepository;

    public void onEnable() {

        try {
            databaseBuilder = new DatabaseBuilder();
            Database database = databaseBuilder.build(this).getDatabase();
            cacheManager = new CacheManager();


            userRepository = new UserRepository(database);

            cacheManager.addCache(userRepository.getCache());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        cacheManager.stop();
        databaseBuilder.closeConnection();
    }
}
