package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OmniMovementHandler {

    private static final float SPRINT_SPEED_MULTIPLIER = 1.3F;
    private static final float AIR_SPRINT_MULTIPLIER = 1.1F; // Leicht reduzierte Geschwindigkeit in der Luft

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {

        Player player = event.getEntity();
        boolean isClientSide = player.level().isClientSide();


        if (isClientSide) {
            KeyMapping sprintKey = Minecraft.getInstance().options.keySprint;
            boolean wantsToSprint = sprintKey.isDown();
            boolean isMoving = event.getInput().leftImpulse != 0 || event.getInput().forwardImpulse != 0;

            if (wantsToSprint && isMoving && !player.isCrouching()) {
                // Für Luft-Sprint: Entferne player.onGround()-Check
                float multiplier = player.onGround() ? SPRINT_SPEED_MULTIPLIER : AIR_SPRINT_MULTIPLIER;

                // Bewegung in alle Richtungen skalieren
                event.getInput().leftImpulse *= multiplier;
                event.getInput().forwardImpulse *= multiplier;

                // Visuellen Sprint-Effekt erzwingen
                if (!player.isSprinting()) {
                    player.setSprinting(true);
                }
            }
        }
    }
}