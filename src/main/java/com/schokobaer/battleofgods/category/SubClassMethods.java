package com.schokobaer.battleofgods.category;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public interface SubClassMethods {
    void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag);

    Component getName(ItemStack name);

    boolean isDamageable(ItemStack stack);

    boolean isFireResistant();

    // Todo: isAutoSwing()
    Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack);
}
