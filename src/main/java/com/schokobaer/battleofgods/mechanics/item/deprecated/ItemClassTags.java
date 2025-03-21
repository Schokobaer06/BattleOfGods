package com.schokobaer.battleofgods.mechanics.item.deprecated;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

@Deprecated
public class ItemClassTags {
    public static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("battleofgods","class/" + name.toLowerCase()));
    }
}

