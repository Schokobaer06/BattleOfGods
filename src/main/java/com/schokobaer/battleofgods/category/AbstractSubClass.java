package com.schokobaer.battleofgods.category;


import com.schokobaer.battleofgods.category.mainClass.MainClass;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.subClass.TerrariaBow;
import com.schokobaer.battleofgods.category.tier.Tier;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractSubClass {
    private MainClass mainClass = null;
    private Rarity rarity = null;
    private Tier tier = null;


    public AbstractSubClass() {
    }

    private static @NotNull String getKnockback(double knockback, Item item) {
        double kb;
        if (item instanceof BowItem)
            kb = knockback;
        else kb = (knockback * 3.5) + 0.5;

        // Determine knockback description
        String kbText;
        if (kb <= 1.0) kbText = "weapon_knockback_no_knockback";
        else if (kb <= 2.0) kbText = "weapon_knockback_extremely_weak";
        else if (kb <= 3.0) kbText = "weapon_knockback_very_weak";
        else if (kb <= 4.5) kbText = "weapon_knockback_weak";
        else {
            if (kb <= 6.0) kbText = "weapon_knockback_average";
            else if (kb <= 7.5) kbText = "weapon_knockback_strong";
            else if (kb <= 9.0) kbText = "weapon_knockback_very_strong";
            else if (kb <= 10.5) kbText = "weapon_knockback_extremely_strong";
            else kbText = "weapon_knockback_insanely_strong";
        }
        return kbText;
    }

    private static @NotNull String getSpeed(double attackSpeed, Item item) {
        // Terraria-UseTime aus Minecraft-APS errechnen
        int useTime;
        if (item instanceof TerrariaBow)
            useTime = (int) attackSpeed;
        else useTime = (int) Math.round(20.0 / attackSpeed);

        // Neue Schwellen (UseTime in Terraria-Frames)
        String speedText;
        if (useTime <= 5) {
            speedText = "weapon_speed_insanely_fast";      // ultraschnell
        } else if (useTime <= 20) {
            speedText = "weapon_speed_very_fast";          // sehr schnell
        } else if (useTime <= 25) {
            speedText = "weapon_speed_fast";               // schnell
        } else if (useTime <= 30) {
            speedText = "weapon_speed_average";            // durchschnittlich
        } else if (useTime <= 35) {
            speedText = "weapon_speed_slow";               // langsam
        } else if (useTime <= 40) {
            speedText = "weapon_speed_very_slow";          // sehr langsam
        } else {
            speedText = "weapon_speed_extremely_slow";     // extrem langsam
        }
        return speedText;
    }

    public MainClass getMainClass() {
        if (mainClass != null) return this.mainClass;
        else return MainClasses.MISC;
    }

    public void setMainClass(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    public Rarity getRarity() {
        if (rarity != null) return rarity;
        else return InitRarity.WHITE.get();
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Tier getTier() {
        if (tier != null) return tier;
        else return InitTier.TIER_1.get();
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Component getName(Component name) {
        return name.copy().withStyle(Style.EMPTY.withColor(this.getRarity().getColor()));
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level level, List<net.minecraft.network.chat.Component> tooltip, TooltipFlag flag) {
        /// NORMAL

        //Rarity
        tooltip.add(1, Component.translatable("rarity.battleofgods." +
                this.getRarity().getDisplayName().toLowerCase()).setStyle(
                Style.EMPTY.withBold(true)
                        .withColor(this.getRarity().getColor())
                        .withItalic(true)
        ));
        //Speed
        if (!(itemstack.getItem() instanceof TerrariaBow)) {
            // Get the attack speed attribute (default to 4.0 if missing)
            double attackSpeed = itemstack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                    .get(Attributes.ATTACK_SPEED).stream()
                    .mapToDouble(AttributeModifier::getAmount)
                    .sum() + 4; // +4.0 because attack speed is offset in Minecraft

            //Convert to Terraria useTime
            String speedText = getSpeed(attackSpeed, itemstack.getItem());

            tooltip.add(Component.translatable("tooltip.battleofgods." + speedText).withStyle(ChatFormatting.DARK_GREEN));

            //Knockback
            // Get attack knockback attribute (default to 0 if missing)
            double mcKnockback = itemstack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                    .get(Attributes.ATTACK_KNOCKBACK).stream()
                    .mapToDouble(AttributeModifier::getAmount)
                    .sum();

            // Convert to Terraria Knockback
            String kbText = getKnockback(mcKnockback, itemstack.getItem());

            tooltip.add(Component.translatable("tooltip.battleofgods." + kbText).withStyle(ChatFormatting.DARK_GREEN));
        }
        /// SWORD

        //Damage
        if (itemstack.getItem() instanceof SwordItem swordItem) {
            float damage = swordItem.getDamage() + 1;
            String damageText = (damage % 1 == 0) ? String.valueOf((int) damage) : String.valueOf(damage);
            tooltip.add(Component.literal(damageText + " " + this.getMainClass().name() + " ")
                    .append(Component.translatable("tooltip.battleofgods.damage"))
                    .withStyle(ChatFormatting.DARK_GREEN));
        }

        /// BOW

        //Damage
        if (itemstack.getItem() instanceof TerrariaBow bowItem) {
            float damage = bowItem.getBaseDamage();
            String damageText = (damage % 1 == 0) ? String.valueOf((int) damage) : String.valueOf(damage);
            tooltip.add(Component.literal(damageText + " " + this.getMainClass().name() + " ")
                    .append(Component.translatable("tooltip.battleofgods.damage"))
                    .withStyle(ChatFormatting.DARK_GREEN));
        }
        //Speed
        if (itemstack.getItem() instanceof TerrariaBow bowItem) {
            double useTime = bowItem.getUseDuration(itemstack);
            String UseTimeText = getSpeed(useTime, itemstack.getItem());
            tooltip.add(Component.translatable("tooltip.battleofgods." + UseTimeText).withStyle(ChatFormatting.DARK_GREEN));
        }
        //Knockback
        if (itemstack.getItem() instanceof TerrariaBow bowItem) {
            double knockback = bowItem.getKnockback();
            String knockbackText = String.valueOf(knockback);
            tooltip.add(Component.translatable("tooltip.battleofgods." + knockbackText).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    public boolean isRepairable(@NotNull ItemStack itemstack) {
        return false;
    }

    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(itemstack.getItem());
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }
}
