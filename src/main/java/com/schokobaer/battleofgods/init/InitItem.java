package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.item.tier1.ItemCopperBroadsword;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);
    public static final RegistryObject<Item> COPPER_BROADSWORD = ITEMS.register("copper_broadsword", ItemCopperBroadsword::new);
}
