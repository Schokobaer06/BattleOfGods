package com.schokobaer.battleofgods.mechanics;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import javax.annotation.Nullable;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Tier {
    private final String name;
    private final TagKey<Item> tag;

    public Tier(String name, TagKey<Item> tag) {
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public TagKey<Item> getTag() {
        return tag;
    }
}
