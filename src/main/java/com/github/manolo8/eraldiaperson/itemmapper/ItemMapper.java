package com.github.manolo8.eraldiaperson.itemmapper;

import com.github.manolo8.simplecraft.data.model.base.BaseEntity;
import org.bukkit.inventory.ItemStack;

public class ItemMapper extends BaseEntity {

    private int index;
    private ItemStack item;
    private int personId;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
