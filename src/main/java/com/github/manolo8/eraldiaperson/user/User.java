package com.github.manolo8.eraldiaperson.user;

import com.github.manolo8.eraldiaperson.Person;
import com.github.manolo8.simplecraft.data.model.named.NamedEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class User extends NamedEntity {

    private UUID uuid;
    private String name;
    private List<Person> persons;
    private Player base;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Player getBase() {
        return base;
    }

    public void setBase(Player base) {
        this.base = base;
    }
}
