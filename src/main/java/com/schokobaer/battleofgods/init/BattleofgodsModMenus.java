
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.schokobaer.battleofgods.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import com.schokobaer.battleofgods.world.inventory.TestGuiMenu;
import com.schokobaer.battleofgods.BattleofgodsMod;

public class BattleofgodsModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BattleofgodsMod.MODID);
	public static final RegistryObject<MenuType<TestGuiMenu>> TEST_GUI = REGISTRY.register("test_gui", () -> IForgeMenuType.create(TestGuiMenu::new));
}
