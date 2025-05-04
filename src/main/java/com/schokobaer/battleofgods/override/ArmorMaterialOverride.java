package com.schokobaer.battleofgods.override;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

@Deprecated
public interface ArmorMaterialOverride {
    int getDurabilityForType(ArmorItemOverride.Type type);

    int getDefenseForType(ArmorItemOverride.Type type);

    int getEnchantmentValue();

    SoundEvent getEquipSound();

    Ingredient getRepairIngredient();

    String getName();

    float getToughness();

    float getKnockbackResistance();
}