package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BattleofgodsMod.MODID);
    public static final RegistryObject<CreativeModeTab> TIER_1 = CREATIVE_MODE_TABS.register("tier_1",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.battleofgods.tier_1"))
                    .icon(() -> new ItemStack(InitItem.COPPER_SHORTSWORD.get()))
                    .displayItems((parameters, tabData) -> {
                        tabData.accept(InitBlocks.WOODEN_WORKBENCH.get().asItem());
                        InitItem.ITEMS.getEntries().forEach(
                                itemRegistryObject -> tabData.accept(itemRegistryObject.get())
                        );
                    })
                    .withSearchBar()
                    .build());
}
