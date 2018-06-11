package com.github.manolo8.eraldiaperson.listener;

import com.github.manolo8.eraldiaperson.user.UserService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

@SuppressWarnings("unused")
public class MainListener implements Listener {

    private final UserService userService;

    public MainListener(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        try {
            userService.join(event.getPlayer());
        } catch (SQLException e) {
            event.getPlayer().kickPlayer("§cHouve um erro");
            e.printStackTrace();
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        userService.quit(event.getPlayer());
    }
}
