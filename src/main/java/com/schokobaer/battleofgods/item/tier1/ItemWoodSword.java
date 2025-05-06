package com.schokobaer.battleofgods.item.tier1;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.classes.subClass.Broadsword;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class ItemWoodSword extends Broadsword {
    public ItemWoodSword() {
        super(0, 0, null, 7, 0.87f, new Properties(), InitRarity.WHITE, InitTier.TIER_1);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create(); // Veränderbare Multimap
        modifiers.putAll(super.getAttributeModifiers(slot, stack)); // Basis-Modifier

        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.put(
                    Attributes.ATTACK_KNOCKBACK,
                    new AttributeModifier(
                            UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), // Eindeutige UUID
                            "weapon_knockback_bonus",
                            0.5, // Knockback-Wert
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return modifiers;
    }
}
