package com.github.manolo8.eraldiaperson;

import com.github.manolo8.eraldiaperson.itemmapper.ItemMapper;
import com.github.manolo8.eraldiaperson.itemmapper.ItemMapperRepository;
import com.github.manolo8.simplecraft.data.model.base.BaseEntity;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.List;

public class Person extends BaseEntity {

    private final ItemMapperRepository repository;
    private float exp;
    private int food;
    private double health;
    private float saturation;
    private Location lastLocation;
    private List<ItemMapper> items;

    public Person(ItemMapperRepository repository) {
        this.repository = repository;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public void save(Player player) throws SQLException {
        exp = player.getExp();
        food = player.getFoodLevel();
        health = player.getHealth();
        saturation = player.getSaturation();
        lastLocation = player.getLocation();

        Inventory inventory = player.getInventory();

        ItemStack[] contents = inventory.getContents();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) continue;

            ItemMapper mapper = repository.create(this, item);
            items.add(mapper);
        }

        player.getInventory().clear();
    }

    public void load(Player player) {
        player.setExp(exp);
        player.setHealth(health);
        player.setSaturation(saturation);
        player.setFoodLevel(food);
        player.teleport(lastLocation);

        Inventory inventory = player.getInventory();

        for (ItemMapper item : items) {
            inventory.setItem(item.getIndex(), item.getItem());
            item.remove();
        }
    }
}
