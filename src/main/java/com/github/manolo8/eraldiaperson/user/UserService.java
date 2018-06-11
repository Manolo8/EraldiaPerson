package com.github.manolo8.eraldiaperson.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserService {

    private List<User> logged;
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
        this.logged = new ArrayList<>();
    }

    public void init() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                join(player);
            } catch (SQLException e) {
                player.kickPlayer("§cHouve um erro");
            }
        }
    }

    public void join(Player player) throws SQLException {
        User user = repository.findOrCreate(player.getUniqueId());

        logged.add(user);

        user.setName(player.getName());
        user.setBase(player);

        join(user);
    }

    public void quit(Player player) {
        Iterator<User> i = logged.iterator();

        while (i.hasNext()) {
            User user = i.next();

            if (user.getUuid().equals(player.getUniqueId())) {
                user.setBase(null);
                quit(user);
                i.remove();
                break;
            }

        }
    }

    private void join(User user) {
        user.addReference();
    }

    private void quit(User user) {
        user.removeReference();
    }

    public List<User> getLogged() {
        return logged;
    }

    public User getLogged(Player player) {
        for (User user : getLogged())
            if (user.getBase() == player) return user;
        return null;
    }

    public User findByName(String name) throws SQLException {
        return repository.findByName(name);
    }

}
