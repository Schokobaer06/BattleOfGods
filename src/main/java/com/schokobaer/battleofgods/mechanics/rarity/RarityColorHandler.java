package com.schokobaer.battleofgods.mechanics.rarity;

import com.mojang.blaze3d.platform.NativeImage;
import com.schokobaer.battleofgods.BattleofgodsMod;
import cpw.mods.modlauncher.api.ITransformationService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RarityColorHandler {
    private static final Map<ResourceLocation, TextureAtlasSprite> TEXTURE_CACHE = new HashMap<>();
    private static float animationProgress = 0;
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.ClientTickEvent.Phase.END) {
            tickCounter++;
            if (tickCounter % 2 == 0) {
                animationProgress += 0.05f;
                if (animationProgress > 1.0f) {
                    animationProgress = 0; // Zurücksetzen, um Überlauf zu vermeiden
                }
            }
        }
    }

    public static int getColor(Rarity rarity) {
        return rarity.getColor().map(
                hex -> hex,
                textureLocation -> {
                    try {
                        Optional<Resource> optionalResource = Minecraft.getInstance().getResourceManager().getResource(textureLocation);
                        if (optionalResource.isPresent()) {
                            Resource resource = optionalResource.get();
                            NativeImage image = NativeImage.read(resource.open());
                            //System.out.println("Texture loaded: " + resource);
                            //System.out.println("Texture size: x: " + image.getWidth() + " y: " + image.getHeight());

                            float progress = (animationProgress * rarity.getAnimationSpeed()) % 1.0f;
                            int x = (int) (progress * (image.getWidth() - 1)); // -1, um Überlauf zu vermeiden
                            int y = (int) (progress * (image.getHeight() - 1));

                            //System.out.println("Sampling pixel at: " + x + ", " + y);
                            //System.out.println("Progress: " + progress + "\nAnimation Progress: " + animationProgress + "\nAnimation Speed: " + rarity.getAnimationSpeed());

                            // Extrahiere die Farbe aus der Textur
                            int color = image.getPixelRGBA(x, y);
                            //System.out.println("Sampled color (ARGB): " + Integer.toHexString(color));
                            return color;
                        } else {
                            System.out.println("Texture not found: " + textureLocation);
                            return 0xFF000000;
                        }
                    } catch (Exception e) {
                        System.out.println("Error loading texture: " + textureLocation);
                        return 0xFF000000;
                    }

                }
        );
    }
}