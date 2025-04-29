package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.item.tier1.ItemCopperBroadsword;
import com.schokobaer.battleofgods.item.tier1.ItemCopperShortsword;
import com.schokobaer.battleofgods.mechanics.item.override.ItemOverride;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);

    // Tier 1
    //Melee
    public static final RegistryObject<ItemOverride> COPPER_BROADSWORD = ITEMS.register("copper_broadsword", ItemCopperBroadsword::new);
    public static final RegistryObject<ItemOverride> COPPER_SHORTSWORD = ITEMS.register("copper_shortsword", ItemCopperShortsword::new);
    //Blocks
    public static final RegistryObject<Item> WOODEN_WORKBENCH = block(InitBlocks.WOODEN_WORKBENCH);



    //Methods
    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
