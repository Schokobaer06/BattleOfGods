package com.schokobaer.battleofgods.category.subClass;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.SubClassMethods;
import com.schokobaer.battleofgods.category.mainClass.MainClass;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.tier.GameTier;
import com.schokobaer.battleofgods.category.tier.Tiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class Broadsword extends SwordItem implements SubClassMethods {
    private final double knockback;
    private final Supplier<AbstractSubClass> subClass;
    private boolean autoSwing = false;

    public Broadsword(int attackDamage, float attackSpeed, double knockback, boolean isAutoSwing) {
        super(Tiers.WHITE, attackDamage - 1, attackSpeed - 4,
                new Properties().durability(0)
                        .defaultDurability(0)
                        .setNoRepair());
        this.knockback = knockback;
        this.autoSwing = isAutoSwing;
        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.MELEE);
            sb.setRarity(Tiers.WHITE.getRarity());
            sb.setTier(tier.get());
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
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create(); // Veränderbare Multimap
        modifiers.putAll(super.getAttributeModifiers(slot, stack)); // Basis-Modifier

        if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
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

    public boolean  isAutoSwing() {
        //TODO
        return false;
    }
    public MainClass getMainClass() {
        return subClass.get().getMainClass();
    }
    public Rarity getRarity() {
        return subClass.get().getRarity();
    }
    public GameTier getGameTier() {
        return subClass.get().getTier();
    }
    public Rarity getItemRarity() {
        return subClass.get().getRarity();
    }


    @Override
    public net.minecraft.world.item.Rarity getRarity(ItemStack stack) {
        return super.getRarity(stack);
    }
    public void setAutoSwing(boolean autoSwing) {
        this.autoSwing = autoSwing;
    }
}

