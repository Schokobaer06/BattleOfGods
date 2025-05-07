package com.schokobaer.battleofgods.category.subClass;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.SubClassMethods;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class Broadsword extends SwordItem implements SubClassMethods {
    private final double knockback;
    private final Supplier<AbstractSubClass> subClass;

    public Broadsword(int attackDamage, float attackSpeed, double knockback, int enchantmentValue, Rarity rarity, com.schokobaer.battleofgods.category.tier.Tier tier) {
        super(new Tier() {
            @Override
            public int getUses() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 0;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return enchantmentValue;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        }, attackDamage - 1, attackSpeed - 4, new Properties());
        this.knockback = knockback;
        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.MELEE);
            sb.setRarity(rarity);
            sb.setTier(tier);
            return sb;
        };
    }

    public Broadsword(Tier gameTier, int attackDamage, float attackSpeed, double knockback, Properties properties, Rarity rarity, com.schokobaer.battleofgods.category.tier.Tier tier) {
        super(gameTier, attackDamage, attackSpeed, properties);
        this.knockback = knockback;
        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.MELEE);
            sb.setRarity(rarity);
            sb.setTier(tier);
            return sb;
        };
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return super.hasCraftingRemainingItem(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, tooltip, flag);
        subClass.get().appendHoverText(itemstack, level, tooltip, flag);
    }

    @Override
    public Component getName(ItemStack stack) {
        return subClass.get().getName(super.getName(stack));
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
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
                            this.knockback, // Knockback-Wert
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return modifiers;
    }
}

