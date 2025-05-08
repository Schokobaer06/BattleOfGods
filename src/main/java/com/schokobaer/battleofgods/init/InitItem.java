package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.armor.tier1.WoodArmor;
import com.schokobaer.battleofgods.category.subClass.TerrariaArmor;
import com.schokobaer.battleofgods.category.subClass.TerrariaBow;
import com.schokobaer.battleofgods.item.tier1.ItemCopperBroadsword;
import com.schokobaer.battleofgods.item.tier1.ItemCopperShortsword;
import com.schokobaer.battleofgods.item.tier1.ItemWoodBow;
import com.schokobaer.battleofgods.item.tier1.ItemWoodSword;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BattleOfGods.MODID);

    // Tier 1
    //Melee
    public static final RegistryObject<SwordItem> COPPER_BROADSWORD = ITEMS.register("copper_broadsword", ItemCopperBroadsword::new);
    public static final RegistryObject<SwordItem> COPPER_SHORTSWORD = ITEMS.register("copper_shortsword", ItemCopperShortsword::new);
    public static final RegistryObject<SwordItem> WOOD_SWORD = ITEMS.register("wood_sword", ItemWoodSword::new);
    //Ranged
    public static final RegistryObject<TerrariaBow> WOOD_BOW = ITEMS.register("wood_bow", ItemWoodBow::new);
    //Magic
    //Summon
    //Throwing
    //Healing
    //Bard
    //Misc
    //Tools
    //Consumables
    //Blocks
    public static final RegistryObject<Item> WOODEN_WORKBENCH = block(InitBlocks.WOODEN_WORKBENCH);
    //Armor
    public static final RegistryObject<TerrariaArmor> WOOD_ARMOR_HELMET = ITEMS.register("wood_armor_helmet", () -> new WoodArmor(ArmorItem.Type.HELMET));
    public static final RegistryObject<TerrariaArmor> WOOD_ARMOR_CHESTPLATE = ITEMS.register("wood_armor_chestplate", () -> new WoodArmor(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<TerrariaArmor> WOOD_ARMOR_LEGGINGS = ITEMS.register("wood_armor_leggings", () -> new WoodArmor(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<TerrariaArmor> WOOD_ARMOR_BOOTS = ITEMS.register("wood_armor_boots", () -> new WoodArmor(ArmorItem.Type.BOOTS));


    //Methods
    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
