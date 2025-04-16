package com.schokobaer.battleofgods.mechanics.handler;

import com.schokobaer.battleofgods.init.InitModifiers;
import com.schokobaer.battleofgods.mechanics.modifier.ModifierRegistry;
import com.schokobaer.battleofgods.mechanics.modifier.modifiers.ModifierCriticalHitChance;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collections;

public class TooltipHandler {
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Attribute critAttribute = ModifierCriticalHitChance.get().getAttribute();

        // Korrektur: Verwende öffentliche Methoden
        double baseValue = ModifierRegistry.getBaseModifierValue(stack.getItem(), critAttribute);
        double totalValue = ModifierRegistry.getTotalValue(stack.getItem(), critAttribute);

        if (totalValue > 0) {
            MutableComponent text = Component.literal("Critical Hit: ")
                    .append(Component.literal(String.format("%.0f%%", totalValue))
                            .withStyle(ChatFormatting.GOLD));

            if (totalValue != baseValue) {
                text.append(" (" + (totalValue > baseValue ? "+" : "") +
                                String.format("%.0f%%)", totalValue - baseValue))
                        .withStyle(ChatFormatting.GRAY);
            }

            event.getToolTip().add(text);
        }
    }
}
