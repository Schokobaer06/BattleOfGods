package com.schokobaer.battleofgods.mechanics.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Locale;

public class ItemSubClassTags {
    public static TagKey<Item> create(String name, ItemClass itemClass) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("battleofgods",itemClass.getName() + "/" + name));
    }
}
