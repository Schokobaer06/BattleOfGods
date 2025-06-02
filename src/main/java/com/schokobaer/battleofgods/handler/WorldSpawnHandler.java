package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldSpawnHandler {
    private static final int SEARCH_RADIUS = 1000;
    private static final int STEP = 16;

    private static class WorldSpawnData extends SavedData {
        public BlockPos validatedSpawn = null;

        public static WorldSpawnData load(CompoundTag tag) {
            WorldSpawnData data = new WorldSpawnData();
            if (tag.contains("validatedSpawn")) {
                int[] pos = tag.getIntArray("validatedSpawn");
                data.validatedSpawn = new BlockPos(pos[0], pos[1], pos[2]);
            }
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            if (validatedSpawn != null) {
                tag.putIntArray("validatedSpawn", new int[]{
                        validatedSpawn.getX(),
                        validatedSpawn.getY(),
                        validatedSpawn.getZ()
                });
            }
            return tag;
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level) || level.dimension() != ServerLevel.OVERWORLD) return;

        WorldSpawnData data = level.getDataStorage().computeIfAbsent(
                WorldSpawnData::load,
                WorldSpawnData::new,
                BattleOfGods.MODID + "_spawn_data"
        );

        if (data.validatedSpawn == null) {
            BlockPos worldSpawn = findValidSurfaceSpawn(level);
            if (worldSpawn != null) {
                level.setDefaultSpawnPos(worldSpawn, 0.0F);
                data.validatedSpawn = worldSpawn;
                data.setDirty();
                BattleOfGods.LOGGER.info("Set world spawn to surface at {}", worldSpawn);
            }
        } else {
            level.setDefaultSpawnPos(data.validatedSpawn, 0.0F);
        }
    }

    private static BlockPos findValidSurfaceSpawn(ServerLevel level) {
        BlockPos originalSpawn = level.getSharedSpawnPos();

        // Check original spawn first
        BlockPos surfacePos = getSurfacePos(level, originalSpawn);
        if (isValidBiome(level, surfacePos)) {
            return surfacePos;
        }

        // Spiral search pattern
        for (int radius = STEP; radius <= SEARCH_RADIUS; radius += STEP) {
            for (int x = -radius; x <= radius; x += STEP) {
                for (int z = -radius; z <= radius; z += STEP) {
                    if (Math.abs(x) < radius && Math.abs(z) < radius) continue;

                    BlockPos checkPos = originalSpawn.offset(x, 0, z);
                    surfacePos = getSurfacePos(level, checkPos);

                    if (isValidBiome(level, surfacePos)) {
                        return surfacePos;
                    }
                }
            }
        }
        return getSurfacePos(level, originalSpawn);
    }

    private static BlockPos getSurfacePos(ServerLevel level, BlockPos pos) {
        // Kombiniere beide Heightmap-Typen für genaue Position
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos);
        BlockPos motion = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, surface);

        return new BlockPos(
                surface.getX(),
                Math.max(surface.getY(), motion.getY()),
                surface.getZ()
        );
    }

    private static boolean isValidBiome(ServerLevel level, BlockPos pos) {
        Holder<Biome> biome = level.getBiome(pos);
        return biome.is(Biomes.PLAINS) || biome.is(Biomes.FOREST);
    }
}