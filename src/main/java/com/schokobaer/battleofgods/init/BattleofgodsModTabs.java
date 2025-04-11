
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BattleofgodsModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BattleofgodsMod.MODID);
	public static final RegistryObject<CreativeModeTab> TIER_1 = REGISTRY.register("tier_1",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.battleofgods.tier_1")).icon(() -> new ItemStack(BattleofgodsModItems.ITEM_TIER_1.get())).displayItems((parameters, tabData) -> {
				tabData.accept(BattleofgodsModBlocks.WOODEN_WORKBENCH.get().asItem());
				InitItem.ITEMS.getEntries().forEach(
						itemRegistryObject -> tabData.accept(itemRegistryObject.get())
				);
			}).withSearchBar().build());
}
