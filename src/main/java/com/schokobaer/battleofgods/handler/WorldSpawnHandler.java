package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// TODO: fixing tp on cheats off

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldSpawnHandler {
    private static final int SEARCH_RADIUS = 4069;
    private static final int STEP = 8;

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level) || level.dimension() != ServerLevel.OVERWORLD) return;

        WorldSpawnData data = level.getDataStorage().computeIfAbsent(
                WorldSpawnData::load,
                WorldSpawnData::new,
                BattleOfGods.MODID + "_world_spawn_data"
        );

        if (!data.spawnInitialized) {
            BattleOfGods.LOGGER.info("Initializing world spawn for the first time");
            BlockPos foundPos = findSpawnPosition(level);

            if (foundPos != null) {
                level.getServer().overworld().setDefaultSpawnPos(foundPos, 0.0F);
                data.customSpawnPos = foundPos;
                data.spawnInitialized = true;
                data.setDirty();
                BattleOfGods.LOGGER.info("World spawn set to: {}", foundPos);
            }
        }
    }

    private static BlockPos adjustToTrueSurface(ServerLevel level, BlockPos pos) {
        // Schritt 1: Finde echte Oberfläche
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos);

        // Schritt 2: Sicherer Standplatz checken
        while (surface.getY() > level.getMinBuildHeight() + 2) {
            if (level.getBlockState(surface).isSolid()
                    && level.getBlockState(surface.above()).isAir()
                    && level.getBlockState(surface.above(2)).isAir()) {
                return surface.above();
            }
            surface = surface.below();
        }
        return surface.above();
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CompoundTag persistentData = player.getPersistentData();
            CompoundTag modData = persistentData.getCompound(BattleOfGods.MODID);

            // Nur bei erstmaligem Join
            if (!modData.contains("initialSpawnDone")) {
                ServerLevel level = player.serverLevel();
                WorldSpawnData worldData = level.getDataStorage().get(
                        WorldSpawnData::load,
                        BattleOfGods.MODID + "_world_spawn_data"
                );

                if (worldData != null && worldData.customSpawnPos != null) {
                    // Teleportation mit Server-side Chunk Loading
                    ChunkPos spawnChunk = new ChunkPos(worldData.customSpawnPos);
                    level.getChunk(spawnChunk.x, spawnChunk.z);

                    player.teleportTo(
                            level,
                            worldData.customSpawnPos.getX() + 0.5,
                            worldData.customSpawnPos.getY(),
                            worldData.customSpawnPos.getZ() + 0.5,
                            player.getYRot(),
                            player.getXRot()
                    );

                    modData.putBoolean("initialSpawnDone", true);
                    persistentData.put(BattleOfGods.MODID, modData);
                    BattleOfGods.LOGGER.info("Teleported new player to custom spawn");
                }
            }
        }
    }

    private static BlockPos findSpawnPosition(ServerLevel level) {
        BlockPos originalSpawn = level.getSharedSpawnPos();

        // Neue Heightmap-Logik für echte Oberflächenberechnung
        BlockPos surfacePos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, originalSpawn);
        surfacePos = adjustToTrueSurface(level, surfacePos);

        if (isValidBiome(level, surfacePos)) {
            return surfacePos;
        }

        // Durchsuche Radius mit optimierter Oberflächenlogik
        for (int r = STEP; r <= SEARCH_RADIUS; r += STEP) {
            for (int x = -r; x <= r; x += STEP) {
                for (int z = -r; z <= r; z += STEP) {
                    if (Math.abs(x) < r && Math.abs(z) < r) continue;

                    BlockPos searchPos = originalSpawn.offset(x, 0, z);
                    BlockPos foundPos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, searchPos);
                    foundPos = adjustToTrueSurface(level, foundPos);

                    if (isValidBiome(level, foundPos)) {
                        return foundPos;
                    }
                }
            }
        }
        return null;
    }


    private static BlockPos adjustToSafeSpawnHeight(ServerLevel level, BlockPos pos) {
        // Sicherstellen, dass 2 Luftblöcke über dem Spawn sind
        while (pos.getY() < level.getMaxBuildHeight() - 2
                && (!level.isEmptyBlock(pos.above()) || !level.isEmptyBlock(pos.above(2)))) {
            pos = pos.above();
        }
        return pos;
    }

    private static boolean isValidBiome(ServerLevel level, BlockPos pos) {
        Holder<Biome> biome = level.getBiome(pos);
        return biome.is(Biomes.PLAINS) || biome.is(Biomes.FOREST);
    }

    // World-specific spawn data
    private static class WorldSpawnData extends SavedData {
        public boolean spawnInitialized = false;
        public BlockPos customSpawnPos = null;

        public static WorldSpawnData load(CompoundTag tag) {
            WorldSpawnData data = new WorldSpawnData();
            data.spawnInitialized = tag.getBoolean("initialized");
            if (tag.contains("spawnPos")) {
                int[] pos = tag.getIntArray("spawnPos");
                data.customSpawnPos = new BlockPos(pos[0], pos[1], pos[2]);
            }
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            tag.putBoolean("initialized", spawnInitialized);
            if (customSpawnPos != null) {
                tag.putIntArray("spawnPos", new int[]{customSpawnPos.getX(), customSpawnPos.getY(), customSpawnPos.getZ()});
            }
            return tag;
        }
    }
}