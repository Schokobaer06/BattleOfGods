package com.schokobaer.battleofgods.handler;


import com.schokobaer.battleofgods.armor.ArmorSetManager;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ArmorSetBonusHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == PlayerTickEvent.Phase.END) {
            ArmorSetManager.applySetBonuses(event.player);
        }
    }
}
