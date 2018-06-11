package com.github.manolo8.simplecraft.data.model.named;

import com.github.manolo8.simplecraft.data.model.base.BaseEntity;

public class NamedEntity extends BaseEntity {

    private String name;
    private String fastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (name != null) this.fastName = name.toLowerCase();
        setModified(true);
    }

    /**
     * @param other em lower case
     * @return true caso de match
     */
    public boolean match(String other) {
        return other.equals(fastName);
    }
}
