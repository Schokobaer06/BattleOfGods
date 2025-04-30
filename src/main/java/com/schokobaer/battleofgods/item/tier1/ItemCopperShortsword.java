package com.schokobaer.battleofgods.item.tier1;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.init.InitAttributes;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.mechanics.item.subClass.Shortsword;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.UUID;

public class ItemCopperShortsword extends Shortsword {
    public ItemCopperShortsword() {
        super(0, 0, Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:ingots/copper"))), 5, 1.7f, new Properties(), InitRarity.WHITE, InitTier.TIER_1);
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
            modifiers.put(
                    Attributes.ATTACK_KNOCKBACK,
                    new AttributeModifier(
                            UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), // Eindeutige UUID
                            "weapon_knockback_bonus",
                            1.0, // Knockback-Wert
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return modifiers;
    }
}

