package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.mechanics.item.ItemClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItemClass {
    public static final DeferredRegister<ItemClass> ITEM_CLASSES = DeferredRegister.create((ResourceLocation) ForgeRegistries.ITEMS, "battleofgods");

    public static final RegistryObject<ItemClass> MISC = ITEM_CLASSES.register("misc", () -> new ItemClass(new ItemClass.Properties(), "Misc"));
    public static final RegistryObject<ItemClass> MELEE = ITEM_CLASSES.register("melee", () -> new ItemClass(new ItemClass.Properties(), "Melee"));
    public static final RegistryObject<ItemClass> RANGED = ITEM_CLASSES.register("ranged", () -> new ItemClass(new ItemClass.Properties(), "Ranged"));
    public static final RegistryObject<ItemClass> MAGIC = ITEM_CLASSES.register("magic", () -> new ItemClass(new ItemClass.Properties(), "Magic"));
    public static final RegistryObject<ItemClass> SUMMONING = ITEM_CLASSES.register("summoning", () -> new ItemClass(new ItemClass.Properties(), "Summoning"));

    public static final RegistryObject<ItemClass> ROGUE = ITEM_CLASSES.register("rogue", () -> new ItemClass(new ItemClass.Properties(), "Rogue"));
    public static final RegistryObject<ItemClass> HEALING = ITEM_CLASSES.register("healing", () -> new ItemClass(new ItemClass.Properties(), "Healing"));
    public static final RegistryObject<ItemClass> BARD = ITEM_CLASSES.register("bard", () -> new ItemClass(new ItemClass.Properties(), "Bard"));
    public static final RegistryObject<ItemClass> TOOL = ITEM_CLASSES.register("tool", () -> new ItemClass(new ItemClass.Properties(), "Tool"));


}
