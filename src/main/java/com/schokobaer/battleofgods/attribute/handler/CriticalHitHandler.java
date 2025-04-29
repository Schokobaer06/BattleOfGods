package com.schokobaer.battleofgods.attribute.handler;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitAttributes;
import net.bettercombat.utils.MathHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

import static com.schokobaer.battleofgods.BattleofgodsMod.LOGGER;
import static com.schokobaer.battleofgods.BattleofgodsMod.isDebug;

// CriticalHitHandler.java
@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID)
public class CriticalHitHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();
            double weaponCrit;
            // Critical-Hit-Chance berechnen
            //double baseCrit = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();
            try {
                weaponCrit = weapon.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .get(InitAttributes.CRITICAL_HIT_CHANCE.get())
                        .stream().mapToDouble(AttributeModifier::getAmount)
                        .sum();
            } catch (
                    Exception e) {
                // Wenn kein Attribut gefunden wird, Standardwert verwenden
                weaponCrit = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();
            }
            if (weaponCrit < 0) {
                weaponCrit = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();
            }
            if (isDebug()) LOGGER.debug(
                    "Weapon Crit: " + weaponCrit +
                            " | Base Crit: " + InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue() +
                            " | Total Crit: " + MathHelper.clamp((float) weaponCrit, 0.0F, 1.0F)
            );
            double totalCrit = MathHelper.clamp((float) weaponCrit, 0.0F, 1.0F);

            // Zufälliger Check
            if (player.getRandom().nextDouble() < totalCrit) {
                if (isDebug()) LOGGER.debug("Critical Hit!");
                // Schaden verdoppeln
                event.setAmount(event.getAmount() * 2);

                // Partikel-Effekt
                player.level().addParticle(
                        ParticleTypes.CRIT,
                        event.getEntity().getX(),
                        event.getEntity().getY() + 1.0,
                        event.getEntity().getZ(),
                        0, 0, 0
                );

                // Sound
                player.level().playSound(
                        null,
                        player.blockPosition(),
                        SoundEvents.PLAYER_ATTACK_CRIT,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );
            }
        }
    }
}