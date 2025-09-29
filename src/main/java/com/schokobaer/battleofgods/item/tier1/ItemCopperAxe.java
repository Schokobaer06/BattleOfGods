package com.schokobaer.battleofgods.item.tier1;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.subClass.TerrariaAxe;
import com.schokobaer.battleofgods.category.tier.GameTiers;
import com.schokobaer.battleofgods.category.tier.Tiers;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class ItemCopperAxe extends TerrariaAxe {
    public ItemCopperAxe() {
        super(Tiers.GOLD,
                21,
                4,
                AbstractSubClass.getAttackSpeedFromUseTime(30),
                4.5,
                com.schokobaer.battleofgods.category.rarity.Rarities.WHITE,
                GameTiers.TIER_1,
                BlockTags.MINEABLE_WITH_AXE
        );
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create(); // Veränderbare Multimap
        modifiers.putAll(super.getAttributeModifiers(slot, stack)); // Basis-Modifier

        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.put(
                    ForgeMod.BLOCK_REACH.get(),
                    new AttributeModifier(
                            UUID.fromString("c1f39d1e-8e6c-4c72-b0dd-55aa3c6781c3"),
                            "tool_range",
                            -1.0D,
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return modifiers;
    }
}
