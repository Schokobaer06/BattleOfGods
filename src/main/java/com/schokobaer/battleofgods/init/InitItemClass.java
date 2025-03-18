package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.mechanics.item.ItemClass;
import com.schokobaer.battleofgods.mechanics.item.ItemClassTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItemClass {
    public static final DeferredRegister<ItemClass> ITEM_CLASSES = DeferredRegister.create((ResourceLocation) ForgeRegistries.ITEMS, "battleofgods");

    public static final RegistryObject<ItemClass> MISC = registerItemClass(new Item.Properties(), "Misc");
    public static final RegistryObject<ItemClass> MELEE = registerItemClass(new Item.Properties(), "Melee");
    public static final RegistryObject<ItemClass> RANGED = registerItemClass(new Item.Properties(), "Ranged");
    public static final RegistryObject<ItemClass> MAGIC = registerItemClass(new Item.Properties(), "Magic");
    public static final RegistryObject<ItemClass> SUMMONING = registerItemClass(new Item.Properties(), "Summoning");

    public static final RegistryObject<ItemClass> ROGUE = registerItemClass(new Item.Properties(), "Rogue");
    public static final RegistryObject<ItemClass> HEALING = registerItemClass(new Item.Properties(), "Healing");
    public static final RegistryObject<ItemClass> BARD = registerItemClass(new Item.Properties(), "Bard");
    public static final RegistryObject<ItemClass> TOOL = registerItemClass(new Item.Properties(), "Tool");

    public static RegistryObject<ItemClass> registerItemClass(Item.Properties properties, String name) {
        TagKey<Item> tag = ItemClassTags.create(name);
        return ITEM_CLASSES.register(name, () -> new ItemClass(properties, name, tag));
    }
}