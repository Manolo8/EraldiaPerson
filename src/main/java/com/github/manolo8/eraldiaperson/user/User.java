package com.github.manolo8.eraldiaperson.user;

import com.github.manolo8.eraldiaperson.Person;
import com.github.manolo8.simplecraft.data.model.named.NamedEntity;

import java.util.List;
import java.util.UUID;

public class User extends NamedEntity {

    private UUID uuid;
    private String name;
    private List<Person> persons;


    public boolean match(UUID uuid) {
        return this.uuid.equals(uuid);
    }
}
