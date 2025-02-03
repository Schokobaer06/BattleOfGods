
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.schokobaer.battleofgods.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import com.schokobaer.battleofgods.item.CopperShortSwordItem;
import com.schokobaer.battleofgods.BattleofgodsMod;

public class BattleofgodsModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);
	public static final RegistryObject<Item> COPPER_SHORT_SWORD = REGISTRY.register("copper_short_sword", () -> new CopperShortSwordItem());
	// Start of user code block custom items
	// End of user code block custom items
}
