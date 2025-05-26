package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldSpawnHandler {
    private static final int SEARCH_RADIUS = 2048;
    private static final int STEP = 8;
    private static final Heightmap.Types HEIGHTMAP_TYPE = Heightmap.Types.WORLD_SURFACE;

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level) || level.dimension() != ServerLevel.OVERWORLD) return;

        WorldSpawnData data = level.getDataStorage().computeIfAbsent(
                WorldSpawnData::load,
                WorldSpawnData::new,
                BattleOfGods.MODID + "_spawn_data"
        );

        if (!data.initialized) {
            BlockPos spawnPos = findValidSpawn(level);
            if (spawnPos != null) {
                level.getServer().overworld().setDefaultSpawnPos(spawnPos, 0.0F);
                data.customSpawnPos = spawnPos;
                data.initialized = true;
                data.setDirty();
                BattleOfGods.LOGGER.info("World spawn initialized at {}", spawnPos);
            }
        }
    }

    private static BlockPos findValidSpawn(ServerLevel level) {
        BlockPos worldSpawn = level.getSharedSpawnPos();

        // Check original spawn first
        BlockPos surfacePos = getSurfacePos(level, worldSpawn);
        if (isValidBiome(level, surfacePos)) {
            return surfacePos;
        }

        // Spiral search pattern
        for (int radius = STEP; radius <= SEARCH_RADIUS; radius += STEP) {
            for (int x = -radius; x <= radius; x += STEP) {
                for (int z = -radius; z <= radius; z += STEP) {
                    if (Math.abs(x) != radius && Math.abs(z) != radius) continue;

                    BlockPos checkPos = worldSpawn.offset(x, 0, z);
                    BlockPos surfaceCheckPos = getSurfacePos(level, checkPos);

                    if (isValidBiome(level, surfaceCheckPos)) {
                        return surfaceCheckPos;
                    }
                }
            }
        }
        return null;
    }


    private static BlockPos getSurfacePos(ServerLevel level, BlockPos pos) {
        // Dynamische Höhenberechnung bei jedem Aufruf
        BlockPos surfacePos = level.getHeightmapPos(HEIGHTMAP_TYPE, pos);

        // Erweiterte Sicherheitsprüfung
        int safetyChecks = 0;
        while (safetyChecks++ < 32 &&
                (!level.getBlockState(surfacePos.below()).isCollisionShapeFullBlock(level, surfacePos.below()) ||
                        !level.isEmptyBlock(surfacePos))) {
            surfacePos = surfacePos.above();
        }

        // Fallback auf ursprüngliche Heightmap
        if (safetyChecks >= 32) {
            surfacePos = level.getHeightmapPos(HEIGHTMAP_TYPE, pos);
        }

        return surfacePos;
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        CompoundTag playerData = player.getPersistentData();
        CompoundTag modData = playerData.getCompound(BattleOfGods.MODID);

        if (!modData.getBoolean("spawnProcessed")) {
            ServerLevel level = player.serverLevel();
            WorldSpawnData worldData = level.getDataStorage().get(
                    WorldSpawnData::load,
                    BattleOfGods.MODID + "_spawn_data"
            );

            if (worldData != null && worldData.customSpawnPos != null) {
                // Force chunk loading
                ChunkPos chunkPos = new ChunkPos(worldData.customSpawnPos);
                level.getChunkSource().addRegionTicket(
                        TicketType.POST_TELEPORT,
                        chunkPos,
                        1,
                        player.getId()
                );

                // Get precise surface position
                BlockPos spawnPos = getSurfacePos(level, worldData.customSpawnPos);

                // Set respawn and teleport
                player.setRespawnPosition(
                        level.dimension(),
                        spawnPos,
                        0.0f,
                        true,
                        false
                );

                player.teleportTo(
                        level,
                        spawnPos.getX() + 0.5,
                        spawnPos.getY(),
                        spawnPos.getZ() + 0.5,
                        player.getYRot(),
                        player.getXRot()
                );

                modData.putBoolean("spawnProcessed", true);
                playerData.put(BattleOfGods.MODID, modData);
                BattleOfGods.LOGGER.info("Teleported player {} to custom spawn {}", player.getName().getString(), spawnPos);
            }
        }
    }

    private static boolean isValidBiome(ServerLevel level, BlockPos pos) {
        Holder<Biome> biome = level.getBiome(pos);
        return biome.is(Biomes.PLAINS) || biome.is(Biomes.FOREST);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !event.isEndConquered()) {
            ServerLevel level = player.serverLevel();
            WorldSpawnData data = level.getDataStorage().get(
                    WorldSpawnData::load,
                    BattleOfGods.MODID + "_spawn_data"
            );

            if (data != null && data.customSpawnPos != null) {
                // Erzwinge Chunk-Loading
                ChunkPos chunkPos = new ChunkPos(data.customSpawnPos);
                level.getChunkSource().addRegionTicket(
                        TicketType.POST_TELEPORT,
                        chunkPos,
                        1,
                        player.getId()
                );

                // Aktualisiere die Oberflächenposition
                BlockPos surfacePos = getSurfacePos(level, data.customSpawnPos);

                // Teleportiere synchron zum Server-Thread
                player.server.execute(() -> player.teleportTo(
                        level,
                        surfacePos.getX() + 0.5,
                        surfacePos.getY(),
                        surfacePos.getZ() + 0.5,
                        player.getYRot(),
                        player.getXRot()
                ));
            }
        }
    }

    private static class WorldSpawnData extends SavedData {
        public BlockPos customSpawnPos = null;
        public boolean initialized = false;

        public static WorldSpawnData load(CompoundTag tag) {
            WorldSpawnData data = new WorldSpawnData();
            data.initialized = tag.getBoolean("initialized");
            if (tag.contains("spawnPos")) {
                int[] pos = tag.getIntArray("spawnPos");
                data.customSpawnPos = new BlockPos(pos[0], pos[1], pos[2]);
            }
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            tag.putBoolean("initialized", initialized);
            if (customSpawnPos != null) {
                tag.putIntArray("spawnPos", new int[]{
                        customSpawnPos.getX(),
                        customSpawnPos.getY(),
                        customSpawnPos.getZ()
                });
            }
            return tag;
        }
    }
}