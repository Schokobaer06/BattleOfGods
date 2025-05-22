package com.schokobaer.battleofgods.item.tier1;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.subClass.Shortsword;
import com.schokobaer.battleofgods.category.tier.GameTiers;
import com.schokobaer.battleofgods.category.tier.Tiers;
import com.schokobaer.battleofgods.init.InitAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class ItemCopperShortsword extends Shortsword {
    public ItemCopperShortsword() {
        super(Tiers.WHITE, 5, 1.54f, 1, false, GameTiers.TIER_1);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create(); // Veränderbare Multimap
        modifiers.putAll(super.getAttributeModifiers(slot, stack)); // Basis-Modifier

        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.put(
                    InitAttributes.CRITICAL_HIT_CHANCE.get(),
                    new AttributeModifier(
                            UUID.fromString("f9d64db9-c48b-459d-9105-7a817a15c763"),
                            "weapon_crit_bonus",
                            0.16,
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return modifiers;
    }
}

