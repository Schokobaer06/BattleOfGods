
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.item.tier1.ItemCopperBroadsword;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import com.schokobaer.battleofgods.item.ItemTier1Item;
import com.schokobaer.battleofgods.item.CopperShortswordItem;
import com.schokobaer.battleofgods.item.CopperBroadswordItem;
import com.schokobaer.battleofgods.BattleofgodsMod;

public class BattleofgodsModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);
	public static final RegistryObject<Item> COPPER_SHORTSWORD = REGISTRY.register("copper_shortsword", () -> new CopperShortswordItem());
	//public static final RegistryObject<Item> COPPER_BROADSWORD = REGISTRY.register("copper_broadsword", () -> new ItemCopperBroadsword());
	public static final RegistryObject<Item> WOODEN_WORKBENCH = block(BattleofgodsModBlocks.WOODEN_WORKBENCH);
	public static final RegistryObject<Item> ITEM_TIER_1 = REGISTRY.register("item_tier_1", () -> new ItemTier1Item());
	// Start of user code block custom items

	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
