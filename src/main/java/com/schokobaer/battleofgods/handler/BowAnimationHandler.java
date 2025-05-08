package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.subClass.TerrariaBow;
import com.schokobaer.battleofgods.init.InitItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BowAnimationHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (RegistryObject<Item> item : InitItem.ITEMS.getEntries()) {
                if (item.get() instanceof TerrariaBow bow) {

                    // Vanilla-ResourceLocations verwenden!
                    ResourceLocation pullLoc = new ResourceLocation("pull");
                    ResourceLocation pullingLoc = new ResourceLocation("pulling");

                    ItemProperties.register(bow, pullingLoc, (stack, level, entity, prop) ->
                            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
                    );

                    ItemProperties.register(bow, pullLoc, (stack, level, entity, prop) -> {
                        if (entity == null || entity.getUseItem() != stack) return 0.0F;
                        return (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / ((bow.getUseDuration(stack) == 72000) ? 20.0F : (float) bow.getUseDuration(stack));
                    });
                }
            }

        });
    }
}

