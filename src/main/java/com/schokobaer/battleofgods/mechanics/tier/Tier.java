package com.schokobaer.battleofgods.mechanics.tier;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Tier {
    private final String name;
    private final TagKey<Tier> tag;

    public Tier(String name, TagKey<Tier> tag) {
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public TagKey<Tier> getTag() {
        return tag;
    }
}
