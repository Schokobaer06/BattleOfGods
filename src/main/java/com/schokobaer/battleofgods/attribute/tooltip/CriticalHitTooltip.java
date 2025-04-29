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

import java.util.Objects;


// CritTooltip.java
@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID)

public class CriticalHitTooltip {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        // Standardwert (4%)
        double DEFAULT_CRIT_CHANCE = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();

        if (stack.getItem() instanceof Item) {
            // Entferne das Standard-Tooltip für Critical Hit Chance
            event.getToolTip().removeIf(component ->
                    component.getString().contains((CharSequence) Component.translatable("attribute.battleofgods.generic.critical_hit")));

            double weaponCrit;
            try {
                weaponCrit = stack.getAttributeModifiers(Objects.requireNonNull(stack.getEquipmentSlot()))
                        .get(InitAttributes.CRITICAL_HIT_CHANCE.get())
                        .stream()
                        .mapToDouble(AttributeModifier::getAmount)
                        .sum();
            } catch (Exception e) {
                weaponCrit = DEFAULT_CRIT_CHANCE;
            }

            //double defaultCrit = InitAttributes.CRITICAL_HIT_CHANCE.get().getDefaultValue();
            //double totalCrit = weaponCrit;
            double bonus = weaponCrit - DEFAULT_CRIT_CHANCE;

            if (bonus > 0) {
                event.getToolTip().add(Component.literal("+")
                        .append(Component.literal(String.format("%.0f%%", bonus * 100)))
                        .append(Component.translatable("tooltip.battleofgods.critical_hit"))
                        .withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
