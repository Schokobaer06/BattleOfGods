
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.schokobaer.battleofgods.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import com.schokobaer.battleofgods.client.gui.GuiCraftingScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BattleofgodsModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(BattleofgodsModMenus.GUI_CRAFTING.get(), GuiCraftingScreen::new);
		});
	}
}
