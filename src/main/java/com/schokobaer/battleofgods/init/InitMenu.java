package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitMenu {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BattleofgodsMod.MODID);
    public static final RegistryObject<MenuType<WorkbenchMenu>> WORKBENCH = MENUS.register("workbench", () -> IForgeMenuType.create(((windowId, inv, data) -> new WorkbenchMenu(windowId, inv))));

}
