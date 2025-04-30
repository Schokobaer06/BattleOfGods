package com.schokobaer.battleofgods.item.tier1;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.init.InitAttributes;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.UUID;

public class ItemCopperBroadsword extends Broadsword {
    public ItemCopperBroadsword() {
        super(0, 0,Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:ingots/copper"))), 9, 1.5f, new Properties(), InitRarity.WHITE, InitTier.TIER_1);
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
                            1.4, // Knockback-Wert
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return modifiers;
    }
}