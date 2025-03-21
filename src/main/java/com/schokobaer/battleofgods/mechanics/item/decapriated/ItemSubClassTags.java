package com.schokobaer.battleofgods.mechanics.item.decapriated;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;


public class ItemSubClassTags {
    public static TagKey<Item> create(String name, RegistryObject<ItemClass> itemClass) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("battleofgods","/" + name.toLowerCase()));
    }
}
