package com.schokobaer.battleofgods.handler;

/*
@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NoItemFovHandler {
    @SubscribeEvent
    public static void onComputeFov(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        if (player.isUsingItem()
                && player.getUseItem().getItem() instanceof TerrariaBow) {
            if (BattleOfGods.isDebug())
                BattleOfGods.LOGGER.debug("NoItemFovHandler: Reset FOV auf 1.0");
            // 1.0 = Standard-FOV‑Multiplikator (kein Zoom, keine Speed‑Vergrößerung)
            event.setNewFovModifier(1.0f);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // nur am Ende eines Ticks ausführen
        if (event.phase != TickEvent.Phase.END) return;
        
        Player player = event.player;
        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        // falls der Modifier noch dran ist, aber der Spieler nicht mehr den TerrariaBow benutzt:
        if (speedAttr != null && speedAttr.hasModifier(TerrariaBow.SPEED_MODIFIER)) {
            if (!player.isUsingItem()
                    || !(player.getUseItem().getItem() instanceof TerrariaBow)) {
                speedAttr.removeModifier(TerrariaBow.SPEED_MODIFIER_UUID);
            }
        }
        if (BattleOfGods.isDebug())
            BattleOfGods.LOGGER.debug("Player Speed: {} \nSpeed-Modifier: {}",
                    player.getSpeed(),
                    Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
    }
}

 */

