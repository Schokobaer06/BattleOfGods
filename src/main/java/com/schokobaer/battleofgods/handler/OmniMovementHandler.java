    package com.schokobaer.battleofgods.handler;

    import com.schokobaer.battleofgods.BattleOfGods;
    import com.schokobaer.battleofgods.category.subClass.TerrariaBow;
    import net.minecraft.client.KeyMapping;
    import net.minecraft.client.Minecraft;
    import net.minecraft.world.entity.player.Player;
    import net.minecraftforge.client.event.ComputeFovModifierEvent;
    import net.minecraftforge.client.event.MovementInputUpdateEvent;
    import net.minecraftforge.eventbus.api.SubscribeEvent;
    import net.minecraftforge.fml.common.Mod;

    @Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class OmniMovementHandler {

        private static final float SPRINT_SPEED_MULTIPLIER = 1.3F;
        private static final float AIR_SPRINT_MULTIPLIER = 1.1F; // Leicht reduzierte Geschwindigkeit in der Luft
        private static boolean isOmniSprinting = false;

        @SubscribeEvent
        public static void onMovementInput(MovementInputUpdateEvent event) {
            Player player = event.getEntity();
            boolean isClientSide = player.level().isClientSide();

            // Omni-Sprint-Logik
            if (isClientSide) {
                KeyMapping sprintKey = Minecraft.getInstance().options.keySprint;
                boolean wantsToSprint = sprintKey.isDown();
                boolean isMoving = event.getInput().leftImpulse != 0 || event.getInput().forwardImpulse != 0;
                boolean isUsingBow = player.isUsingItem() && player.getUseItem().getItem() instanceof TerrariaBow;

                isOmniSprinting = isMoving && wantsToSprint && !player.isCrouching();

                if (isOmniSprinting) {
                    float multiplier = player.onGround() ? SPRINT_SPEED_MULTIPLIER : AIR_SPRINT_MULTIPLIER;

                    // Kompensiere die Vanilla-Bogen-Verlangsamung
                    if (isUsingBow) {
                        multiplier /= 0.7F; // Vanilla reduziert auf 70 %, wir heben das auf
                    }

                    event.getInput().leftImpulse *= multiplier;
                    event.getInput().forwardImpulse *= multiplier;

                    if (!player.isSprinting()) {
                        player.setSprinting(true);
                    }
                }

                // Zusätzlich: Verhindere die Vanilla-Verlangsamung komplett bei Bogen-Nutzung
                if (isUsingBow) {
                    event.getInput().leftImpulse /= 0.7F;
                    event.getInput().forwardImpulse /= 0.7F;
                }
            }
        }

        // Füge diese neue Event-Methode in der OmniMovementHandler-Klasse hinzu
        @SubscribeEvent
        public static void onFovUpdate(ComputeFovModifierEvent event) {

            if (isOmniSprinting) {
                // Behalte existierende FOV-Modifikationen bei und addiere den Sprint-Effekt
                float currentFov = event.getNewFovModifier();
                float sprintFovAddition = 0.15F; // Vanilla-Wert

                // Nur anwenden, wenn nicht bereits höherer Wert durch andere Mods
                event.setNewFovModifier(currentFov + sprintFovAddition);
            }
        }
    }