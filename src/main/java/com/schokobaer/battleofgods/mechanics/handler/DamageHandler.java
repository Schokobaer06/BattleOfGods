package com.schokobaer.battleofgods.mechanics.handler;

import com.schokobaer.battleofgods.init.InitModifiers;
import com.schokobaer.battleofgods.mechanics.modifier.ModifierRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DamageHandler {
    @SubscribeEvent
    public static void onDamage(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();
            double critChance = ModifierRegistry.getTotalValue(weapon.getItem(), InitModifiers.MODIFIER_CRITICAL_HIT_CHANCE.get());

            if (player.getRandom().nextDouble() * 100 < critChance) {
                event.setAmount(event.getAmount() * 2); // 100% mehr Schaden
                spawnCritEffects(player);
            }
        }
    }

    private static void spawnCritEffects(Player player) {
        // Partikel und Sound Effekte
        player.level().addParticle(ParticleTypes.CRIT,
                player.getX(),
                player.getY() + 1.0,
                player.getZ(),
                0, 0, 0);

        player.playSound(SoundEvents.PLAYER_ATTACK_CRIT,
                1.0F,
                1.0F + player.getRandom().nextFloat() * 0.4F);
    }
}