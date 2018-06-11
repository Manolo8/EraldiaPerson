package com.github.manolo8.eraldiaperson.item;

import com.github.manolo8.simplecraft.data.model.base.BaseEntity;
import org.bukkit.inventory.ItemStack;

public class Item extends BaseEntity {

    private ItemStack itemStack;
    private int type;
    private int hash;

    public ItemStack get() {
        return itemStack;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public void set(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.type = itemStack.getTypeId();
        this.hash = itemStack.hashCode();
    }

    public int getType() {
        return type;
    }
}
