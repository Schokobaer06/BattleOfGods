package com.schokobaer.battleofgods.mechanics.rarity;

import com.mojang.blaze3d.platform.NativeImage;
import com.schokobaer.battleofgods.BattleofgodsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RarityColorHandler {
    private static float animationProgress = 0;
    private static int tickCounter = 0; // Counter to track ticks

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.ClientTickEvent.Phase.END) {
            tickCounter++;
            if (tickCounter >= 2) { // Update every 2nd tick
                animationProgress += 0.003f; // Increment by a small fixed amount
                if (animationProgress > 1.0f) {
                    animationProgress = 0; // Reset to 0 to cycle
                }
                tickCounter = 0; // Reset the counter
            }
        }
    }

    public static Integer getColor(Rarity rarity) {
        return rarity.getEitherIntegerResourceLocation().map(
                hex -> hex,
                textureLocation -> {
                    try {
                        Optional<Resource> optionalResource = Minecraft.getInstance().getResourceManager().getResource(textureLocation);
                        if (optionalResource.isPresent()) {
                            Resource resource = optionalResource.get();
                            NativeImage image = NativeImage.read(resource.open());

                            float progress = (animationProgress * rarity.getAnimationSpeed() * image.getWidth()) % 1.0f;
                            int x = (int) (progress * image.getWidth());
                            int y = image.getHeight() / 2;

                            return image.getPixelRGBA(x, y);
                        } else {
                            BattleofgodsMod.LOGGER.error("Texture not found: {}", textureLocation);
                            return 0xFF000000;
                        }
                    } catch (Exception e) {
                        BattleofgodsMod.LOGGER.error("Error loading texture: {}", textureLocation, e);
                        return 0xFF000000;
                    }
                }
        );
    }
}