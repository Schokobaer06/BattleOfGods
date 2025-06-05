package com.schokobaer.battleofgods.category.subClass;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.SubClassMethods;
import com.schokobaer.battleofgods.category.mainClass.MainClass;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarities;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.tier.GameTier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

import static com.schokobaer.battleofgods.category.AbstractSubClass.getStyle;

public abstract class Shortsword extends SwordItem implements SubClassMethods {
    private final double knockback;
    private final AbstractSubClass subClass = new AbstractSubClass() {
    };
    private boolean autoSwing;

    public Shortsword(Tier tier, int attackDamage, float attackSpeed, double knockback, boolean isAutoSwing, Rarities rarity, GameTier gameTier) {
        super(AbstractSubClass.getTier(tier, rarity.getEnchantmentLevel()), attackDamage - 1, attackSpeed - 4,
                new Properties()
                        .durability(0)
                        .defaultDurability(0)
                        .setNoRepair()
                        .rarity(rarity.asMinecraftRarity())
        );
        this.knockback = knockback;
        this.autoSwing = isAutoSwing;
        this.subClass.setMainClass(MainClasses.MELEE);
        this.subClass.setRarity(rarity.getRarity());
        this.subClass.setGameTier(gameTier);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return super.hasCraftingRemainingItem(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        subClass.appendHoverText(itemstack, level, tooltip, flag);
        super.appendHoverText(itemstack, level, tooltip, flag);

        for (int i = 0; i < tooltip.size(); i++) {
            if (tooltip.get(i).contains(Component.translatable("tooltip.battleofgods.damage"))) {
                // Überschreibe den Tooltip an diesem Index
                float damage = this.getDamage();
                String damageText = (damage % 1 == 0)
                        ? String.valueOf((int) damage) // If damage is a whole number, show as integer
                        : String.format("%.1f", damage);
                tooltip.set(i, Component.literal(damageText + " true " + this.getMainClass().getName() + " ")
                        .append(Component.translatable("tooltip.battleofgods.damage"))
                        .withStyle(getStyle()));
                break;
            }
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return subClass.getName(super.getName(stack));
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

    // Not implemented correctly bc issue with better combat
    public boolean isAutoSwing() {
        return true;
    }

    public void setAutoSwing(boolean autoSwing) {
        this.autoSwing = autoSwing;
    }

    public MainClass getMainClass() {
        return subClass.getMainClass();
    }

    public Rarity getRarity() {
        return subClass.getRarity();
    }

    public void setRarity(Rarity rarity) {
        subClass.setRarity(rarity);
    }

    public GameTier getGameTier() {
        return subClass.getGameTier();
    }

    @Override
    public int getKnockback() {
        return (int) knockback;
    }

    public float getDamage() {
        return super.getDamage();
    }
}

