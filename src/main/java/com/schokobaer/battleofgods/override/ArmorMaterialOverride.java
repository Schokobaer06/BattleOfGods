package com.schokobaer.battleofgods.override;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

public interface ArmorMaterialOverride {
    int getDurabilityForType(ArmorItemOverride.Type armorType);

    int getDefenseForType(ArmorItemOverride.Type armorType);

    int getEnchantmentValue();

    SoundEvent getEquipSound();

    Ingredient getRepairIngredient();

    String getName();

    float getToughness();

    float getKnockbackResistance();
}
