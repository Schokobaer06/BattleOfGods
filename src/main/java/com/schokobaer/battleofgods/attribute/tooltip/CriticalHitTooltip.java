package com.schokobaer.battleofgods.attribute.tooltip;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitAttributes;
import com.schokobaer.battleofgods.mechanics.item.override.ItemOverride;
import net.bettercombat.utils.MathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/*
// CritTooltip.java
@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID)

public class CriticalHitTooltip {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        double weaponCrit;
        if (stack.getItem() instanceof Item) {
            try {
                weaponCrit = stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .get(InitAttributes.CRITICAL_HIT_CHANCE.get())
                        .stream()
                        .mapToDouble(AttributeModifier::getAmount)
                        .sum();
            } catch (NullPointerException e) {
                // Wenn kein Attribut gefunden wird, Standardwert verwenden
                weaponCrit = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();
            }
                double bonus = weaponCrit - InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();

            if (BattleofgodsMod.isDebug())
                BattleofgodsMod.LOGGER.debug("Tooltip Weapon Crit: {} | Base Crit: {} | Total Crit: {} | Bonus: {}",
                        weaponCrit, InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue(),
                        MathHelper.clamp((float) weaponCrit, 0.0F, 1.0F), bonus);
            if (bonus > 0) {
                event.getToolTip().add(Component.literal("+")
                        .append(Component.literal(String.format("%.0f%%", bonus * 100)))
                        .append(Component.translatable("tooltip.battleofgods.critical_hit"))
                        .withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
*/