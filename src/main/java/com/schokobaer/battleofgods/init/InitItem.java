package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.armor.tier1.WoodArmor;
import com.schokobaer.battleofgods.item.tier1.ItemCopperBroadsword;
import com.schokobaer.battleofgods.item.tier1.ItemCopperShortsword;
import com.schokobaer.battleofgods.override.ItemOverride;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);

    // Tier 1
    //Melee
    public static final RegistryObject<ItemOverride> COPPER_BROADSWORD = ITEMS.register("copper_broadsword", ItemCopperBroadsword::new);
    public static final RegistryObject<ItemOverride> COPPER_SHORTSWORD = ITEMS.register("copper_shortsword", ItemCopperShortsword::new);
    //Blocks
    public static final RegistryObject<Item> WOODEN_WORKBENCH = block(InitBlocks.WOODEN_WORKBENCH);
    //Armor
    public static final RegistryObject<WoodArmor> WOOD_ARMOR_HELMET = ITEMS.register("wood_armor_helmet", () -> new WoodArmor(ArmorItem.Type.HELMET, new Item.Properties().fireResistant()));
    public static final RegistryObject<WoodArmor> WOOD_ARMOR_CHESTPLATE = ITEMS.register("wood_armor_chestplate", () -> new WoodArmor(ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()));
    public static final RegistryObject<WoodArmor> WOOD_ARMOR_LEGGINGS = ITEMS.register("wood_armor_leggings", () -> new WoodArmor(ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant()));
    public static final RegistryObject<WoodArmor> WOOD_ARMOR_BOOTS = ITEMS.register("wood_armor_boots", () -> new WoodArmor(ArmorItem.Type.BOOTS, new Item.Properties().fireResistant()));


    //Methods
    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
