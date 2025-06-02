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

import static com.schokobaer.battleofgods.category.AbstractSubClass.getStyle;

public abstract class Shortsword extends SwordItem implements SubClassMethods {
    private final double knockback;
    private final Supplier<AbstractSubClass> subClass;
    private boolean autoSwing;

    public Shortsword(Tier tier, int attackDamage, float attackSpeed, double knockback, boolean isAutoSwing, GameTier gameTier) {
        super(tier, attackDamage - 1, attackSpeed - 4,
                new Properties()
                        .durability(0)
                        .defaultDurability(0)
                        .setNoRepair()
                        .rarity(tier instanceof Tiers ? ((Tiers) tier).getRarity().asMinecraftRarity() : Rarities.WHITE.getRarity().asMinecraftRarity()));
        this.knockback = knockback;
        this.autoSwing = isAutoSwing;
        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.MELEE);
            if (tier instanceof com.schokobaer.battleofgods.category.tier.Tiers) {
                sb.setRarity(((Tiers) tier).getRarity());
            } else {
                sb.setRarity(Rarities.WHITE.getRarity());
            }
            sb.setGameTier(gameTier);
            return sb;
        };
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return super.hasCraftingRemainingItem(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        subClass.get().appendHoverText(itemstack, level, tooltip, flag);
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

    public boolean isAutoSwing() {
        return true;
    }

    public void setAutoSwing(boolean autoSwing) {
        this.autoSwing = autoSwing;
    }

    public MainClass getMainClass() {
        return subClass.get().getMainClass();
    }

    public Rarity getRarity() {
        return subClass.get().getRarity();
    }

    public void setRarity(Rarity rarity) {
        subClass.get().setRarity(rarity);
    }

    public GameTier getGameTier() {
        return subClass.get().getGameTier();
    }

    @Override
    public net.minecraft.world.item.Rarity getRarity(ItemStack stack) {
        Item item = stack.getItem();
        if (item.getClass().getSuperclass() != null && SubClassMethods.class.isAssignableFrom(item.getClass().getSuperclass())) {
            SubClassMethods subClassItem = (SubClassMethods) item.getClass().getSuperclass().cast(item);
            return subClassItem.getRarity().asMinecraftRarity();
        }
        return super.getRarity(stack);
    }

    @Override
    public int getKnockback() {
        return (int) knockback;
    }

    public float getDamage() {
        return super.getDamage();
    }
}

