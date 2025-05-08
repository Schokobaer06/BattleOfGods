package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.block.WoodenWorkbenchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BattleOfGods.MODID);
    public static final RegistryObject<Block> WOODEN_WORKBENCH = BLOCKS.register("wooden_workbench", WoodenWorkbenchBlock::new);
}
