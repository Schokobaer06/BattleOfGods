
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.block.WoodenWorkbenchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BattleofgodsModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BattleofgodsMod.MODID);
	public static final RegistryObject<Block> WOODEN_WORKBENCH = REGISTRY.register("wooden_workbench", () -> new WoodenWorkbenchBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
