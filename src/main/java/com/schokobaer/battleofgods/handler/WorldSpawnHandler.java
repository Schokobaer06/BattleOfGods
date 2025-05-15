package com.schokobaer.battleofgods.handler;


import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldSpawnHandler {
    private static final int SEARCH_RADIUS = 2048; // 2048 Blocks in all directions
    private static final int STEP = 8;

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        BattleOfGods.LOGGER.info("Setting world spawn");
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (level.dimension() == ServerLevel.OVERWORLD) {
            BlockPos originalSpawn = level.getSharedSpawnPos();
            boolean found = false;

            // Prüfe zuerst den Original-Spawn
            if (isValidBiome(level, originalSpawn)) {
                found = true;
            } else {
                // Durchsuche in konzentrischen Kreisen
                for (int r = STEP; r <= SEARCH_RADIUS && !found; r += STEP) {
                    for (int x = -r; x <= r; x += STEP) {
                        for (int z = -r; z <= r; z += STEP) {
                            if (Math.abs(x) < r && Math.abs(z) < r) continue;

                            BlockPos pos = originalSpawn.offset(x, 0, z);
                            pos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);

                            if (isValidBiome(level, pos)) {
                                level.getServer().overworld().setDefaultSpawnPos(pos, 0.0F);
                                BattleOfGods.LOGGER.info("Found valid spawn at: {}", pos);
                                found = true;
                                break;
                            }
                        }
                        if (found) break;
                    }
                    if (found) break;
                }
            }
        }
    }

    private static boolean isValidBiome(ServerLevel level, BlockPos pos) {
        Holder<Biome> biome = level.getBiome(pos);
        return biome.is(Biomes.PLAINS) || biome.is(Biomes.FOREST);
    }
}
