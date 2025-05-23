package com.schokobaer.battleofgods.category;

import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.mainClass.MainClass;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.tier.GameTier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public interface SubClassMethods {

    Component getName(ItemStack name);

    boolean isDamageable(ItemStack stack);

    boolean isFireResistant();

    Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack);

    boolean isAutoSwing();

    void setAutoSwing(boolean autoSwing);

    MainClass getMainClass();

    Rarity getRarity();

    void setRarity(Rarity rarity);

    net.minecraft.world.item.Rarity getRarity(ItemStack stack);

    GameTier getGameTier();

    int getKnockback();

    float getDamage();

}
