
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.schokobaer.battleofgods.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import com.schokobaer.battleofgods.item.ItemCopperShortswordItem;
import com.schokobaer.battleofgods.item.CopperShortswordItem;
import com.schokobaer.battleofgods.item.CopperBroadswordItem;
import com.schokobaer.battleofgods.BattleofgodsMod;

public class BattleofgodsModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);
	public static final RegistryObject<Item> ITEM_COPPER_SHORTSWORD = REGISTRY.register("item_copper_shortsword", () -> new ItemCopperShortswordItem());
	public static final RegistryObject<Item> COPPER_SHORTSWORD = REGISTRY.register("copper_shortsword", () -> new CopperShortswordItem());
	public static final RegistryObject<Item> COPPER_BROADSWORD = REGISTRY.register("copper_broadsword", () -> new CopperBroadswordItem());
	// Start of user code block custom items
	// End of user code block custom items
}
