package com.schokobaer.battleofgods.item.tier1;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.UUID;

public class ItemCopperShortsword extends Shortsword {
    public ItemCopperShortsword() {
        super(0, 0, Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:ingots/copper"))), 3, 1.7f, new Properties(), InitRarity.WHITE, InitTier.TIER_1);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.put(
                    InitAttributes.CRITICAL_HIT_CHANCE.get(),
                    new AttributeModifier(
                            UUID.fromString("1a2b3c4d-5e6f-7g8h-9i0j-1k2l3m4n5o6p"), // Feste UUID
                            "weapon_crit_bonus",
                            0.16,
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return modifiers;
    }

}
