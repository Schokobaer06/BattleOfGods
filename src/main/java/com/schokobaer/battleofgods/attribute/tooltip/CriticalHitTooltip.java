package com.schokobaer.battleofgods.attribute.tooltip;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID)
@OnlyIn(Dist.CLIENT)
public class CriticalHitTooltip {

    private static final Map<ItemStack, Component> tooltipCache = new HashMap<>();
    private static final Map<ItemStack, Long> lastUpdateTimes = new HashMap<>();
    private static final long UPDATE_INTERVAL_MS = 1000; // 1 Sekunde

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        event.getToolTip().removeIf(component -> component.getString().contains(
                Component.translatable("attribute.battleofgods.generic.critical_hit").getString()
        ));
        if (!stack.isEmpty() && stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(InitAttributes.CRITICAL_HIT_CHANCE.get())) {
            long currentTime = System.currentTimeMillis();
            long lastUpdateTime = lastUpdateTimes.getOrDefault(stack, 0L);

            // Prüfe, ob der Cache aktualisiert werden muss
            if (currentTime - lastUpdateTime >= UPDATE_INTERVAL_MS || !tooltipCache.containsKey(stack)) {

                double defaultCritChance = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();
                double weaponCrit = stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .get(InitAttributes.CRITICAL_HIT_CHANCE.get())
                        .stream()
                        .mapToDouble(AttributeModifier::getAmount)
                        .sum();
                double bonus = weaponCrit - defaultCritChance;

                // Tooltip-Text generieren
                Component tooltipText = bonus > 0
                        ? Component.literal("+")
                        .append(Component.literal(String.format("%.0f%%", weaponCrit * 100)))
                        .append(" ")
                        .append(Component.translatable("tooltip.battleofgods.critical_hit"))
                        .withStyle(ChatFormatting.BLUE)
                        : Component.empty();

                tooltipCache.put(stack, tooltipText);
                lastUpdateTimes.put(stack, currentTime);
            }

            // Tooltip aus dem Cache hinzufügen
            Component cachedTooltip = tooltipCache.get(stack);
            if (!cachedTooltip.getString().isEmpty()) {
                event.getToolTip().add(cachedTooltip);
            }
        }
    }
}