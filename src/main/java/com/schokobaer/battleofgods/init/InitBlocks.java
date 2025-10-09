package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.block.WoodenWorkbenchBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BattleOfGods.MODID);
    public static final RegistryObject<Block> WOODEN_WORKBENCH = BLOCKS.register("wooden_workbench", WoodenWorkbenchBlock::new);

    //Ores - diese sind korrekt!
    public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE)
                    .requiresCorrectToolForDrops(), UniformInt.of(2, 5)));

    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = BLOCKS.register("deepslate_tin_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE)
                    .requiresCorrectToolForDrops(), UniformInt.of(2, 5)));

    // Falls du spezifische Properties willst:
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 6.0F) // Genau wie Copper Block
                    .sound(SoundType.COPPER)));

    public static final RegistryObject<Block> RAW_TIN_BLOCK = BLOCKS.register("raw_tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F) // Genau wie Raw Copper Block
                    .sound(SoundType.STONE)));
}