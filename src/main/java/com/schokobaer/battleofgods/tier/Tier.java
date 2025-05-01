package com.schokobaer.battleofgods.tier;

import net.minecraft.tags.TagKey;

public class Tier {
    private final String name;
    private final TagKey<Tier> tag;

    public Tier(String name, TagKey<Tier> tag) {
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return this.name;
    }

    public TagKey<Tier> getTag() {
        return this.tag;
    }
}
