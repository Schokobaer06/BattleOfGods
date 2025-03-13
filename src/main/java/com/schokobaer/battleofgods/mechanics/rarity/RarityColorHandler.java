    package com.schokobaer.battleofgods.mechanics.rarity;

    import com.schokobaer.battleofgods.BattleofgodsMod;
    import net.minecraft.client.Minecraft;
    import net.minecraft.client.renderer.texture.TextureAtlasSprite;
    import net.minecraft.resources.ResourceLocation;
    import net.minecraft.world.inventory.InventoryMenu;
    import net.minecraftforge.api.distmarker.Dist;
    import net.minecraftforge.api.distmarker.OnlyIn;
    import net.minecraftforge.event.TickEvent;
    import net.minecraftforge.eventbus.api.SubscribeEvent;
    import net.minecraftforge.fml.common.Mod;

    import java.util.HashMap;
    import java.util.Map;

    @OnlyIn(Dist.CLIENT) // Only run on the client
    @Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class RarityColorHandler {
        private static final Map<ResourceLocation, TextureAtlasSprite> TEXTURE_CACHE = new HashMap<>();
        private static float animationProgress = 0;

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.ClientTickEvent.Phase.END){
                if (animationProgress > 1.0f) {
                    animationProgress = 0; // Zurücksetzen, um Überlauf zu vermeiden
                }
                else
                    animationProgress += 0.05f;
            }
        }

        /**
         * @return int ARGB color of the rarity
         */
        public static int getColor(Rarity rarity){
            return rarity.getColor().map(
                //hex
                hex ->  hex,
                //animated
                    // Load texture from cache or atlas
                    textureLocation -> {
                    TextureAtlasSprite sprite = TEXTURE_CACHE.computeIfAbsent(
                            textureLocation,
                            loc -> Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(loc)
                    );
                    // Calculate current position in Color Gradient
                    float progress = (animationProgress * rarity.getAnimationSpeed()) % 1.0f;
                    int x = (int) (progress * sprite.contents().width());
                    int y = (int) (progress * sprite.contents().height());
                    // Get color from current position
                    return sprite.getPixelRGBA(0,x, y);
                }
            );
        }
    }