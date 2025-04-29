package com.schokobaer.battleofgods.attribute.handler;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitAttributes;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.schokobaer.battleofgods.BattleofgodsMod.LOGGER;
import static com.schokobaer.battleofgods.BattleofgodsMod.isDebug;

@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID)
public class CriticalHitHandler {
    private static Double DEFAULT_CRIT_CHANCE = 0.0; // Standardwert (4%)
    private static final Map<UUID, Double> cachedCritChance = new HashMap<>();
    private static final Map<UUID, Long> lastUpdate = new HashMap<>();

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DEFAULT_CRIT_CHANCE = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();


        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        // Aktualisiere Cache nur, wenn nötig
        if (shouldUpdateCache(playerId, currentTime)) {
            double totalCritChance = calculateTotalCritChance(player);
            cachedCritChance.put(playerId, totalCritChance);
            lastUpdate.put(playerId, currentTime);
        }

        double totalCritChance = cachedCritChance.getOrDefault(playerId, 0.0);

        if (isDebug()) {
            LOGGER.debug("Total Crit Chance: {} Damage: {}", totalCritChance, event.getAmount());
        }

        if (player.getRandom().nextDouble() < totalCritChance) {
            if (isDebug()) LOGGER.debug("Critical Hit!: {}", event.getAmount() * 2);
            applyCriticalHitEffects(player, event);
        }
    }

    private static boolean shouldUpdateCache(UUID playerId, long currentTime) {
        return !cachedCritChance.containsKey(playerId) ||
                (currentTime - lastUpdate.getOrDefault(playerId, 0L)) > 500; // Aktualisierung alle 1 Sekunde
    }

    private static double calculateTotalCritChance(Player player) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        double mainHandCrit = getCritChance(mainHandItem, EquipmentSlot.MAINHAND);
        double offHandCrit = getCritChance(offHandItem, EquipmentSlot.OFFHAND);

        return Math.min(mainHandCrit + offHandCrit, 1.0);
    }

    private static double getCritChance(ItemStack item, EquipmentSlot slot) {
        if (item.isEmpty()) {
            return 0.0;
        }

        double critChance = 0.0;
        var modifiers = item.getAttributeModifiers(slot).get(InitAttributes.CRITICAL_HIT_CHANCE.get());
        if (modifiers.isEmpty()) {
            return DEFAULT_CRIT_CHANCE; // Standardwert, wenn das Attribut nicht existiert
        }

        for (AttributeModifier modifier : modifiers) {
            critChance += modifier.getAmount();
        }

        return critChance;
    }

    private static void applyCriticalHitEffects(Player player, LivingHurtEvent event) {
        // Schaden verdoppeln
        event.setAmount(event.getAmount() * 2);

        // Nur auf der Client-Seite Partikel und Sounds erzeugen
        if (!player.level().isClientSide) {
            return;
        }

        // Partikel erzeugen
        player.level().addParticle(
                ParticleTypes.CRIT,
                event.getEntity().getX() + player.getRandom().nextGaussian() * 0.5,
                event.getEntity().getY() + event.getEntity().getBbHeight() / 2.0 + player.getRandom().nextGaussian() * 0.5,
                event.getEntity().getZ() + player.getRandom().nextGaussian() * 0.5,
                0.0, 0.0, 0.0
        );
        // Sound abspielen
        player.level().playSound(
                player, // Kein spezifischer Spieler
                player.blockPosition(), // Position des Spielers
                SoundEvents.PLAYER_ATTACK_CRIT, // Soundtyp
                SoundSource.PLAYERS, // Soundquelle
                1.0F, // Lautstärke
                1.0F // Tonhöhe
        );
    }
}