package com.schokobaer.battleofgods.attribute.handler;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitAttributes;
import net.bettercombat.utils.MathHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

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
                weaponCrit = (long) weapon.getAttributeModifiers(Objects.requireNonNull(player.getMainHandItem().getEquipmentSlot()))
                        .get(InitAttributes.CRITICAL_HIT_CHANCE.get())
                        .size();
            } catch (
                    NullPointerException e) {
                // Wenn kein Attribut gefunden wird, Standardwert verwenden
                weaponCrit = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();
            }
            if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug(
                    "Weapon Crit: " + weaponCrit +
                            " | Base Crit: " + InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue() +
                            " | Total Crit: " + (weaponCrit + InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue())
            );
            double totalCrit = MathHelper.clamp((float) weaponCrit, 0.0F, 1.0F);

            // Zufälliger Check
            if (player.getRandom().nextDouble() < totalCrit) {
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