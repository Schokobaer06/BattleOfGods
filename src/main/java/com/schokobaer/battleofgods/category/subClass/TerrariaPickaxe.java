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
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Objects;

public class TerrariaPickaxe extends PickaxeItem implements SubClassMethods {
    private int miningSpeed;
    private final AbstractSubClass subClass = new AbstractSubClass() {
    };

    public TerrariaPickaxe(Tier tier, int miningSpeed, int attackDamage, float attackSpeed, Rarities rarity, GameTier gameTier, TagKey<Block> blocktag) {
        super(AbstractSubClass.getTier(tier, rarity.getEnchantmentLevel(), AbstractSubClass.getDestroySpeedFromMiningSpeed(miningSpeed), blocktag), attackDamage, attackSpeed, new Properties()
                .durability(0)
                .defaultDurability(0)
                .setNoRepair()
                .rarity(rarity.asMinecraftRarity())
        );
        this.subClass.setMainClass(MainClasses.TOOL);
        this.subClass.setRarity(rarity.getRarity());
        this.subClass.setGameTier(gameTier);
        this.miningSpeed = miningSpeed;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return super.hasCraftingRemainingItem(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        subClass.appendHoverText(itemstack, level, tooltip, flag);
        super.appendHoverText(itemstack, level, tooltip, flag);
/*
        for (Component component : tooltip) {
            // Prüft, ob der Inhalt der Komponente übersetzbar ist
            if (component.getContents() instanceof TranslatableContents translatableContents) {
                // Prüft, ob der Übersetzungsschlüssel mit dem gewünschten Text beginnt
                if (translatableContents.getKey().startsWith("tooltip.battleofgods.weapon_knockback_")) {
                    tooltip.add(
                            Component.translatable("tooltip.battleofgods.mining_speed").withStyle(getStyle())
                                    .append(" ")
                                    .append(String.valueOf(miningSpeed)).withStyle(ChatFormatting.WHITE));
                }
            }
        }

 */
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

    public boolean isAutoSwing() {
        return true;
    }

    public void setAutoSwing(boolean autoSwing) {

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

    public int getKnockback() {
        ItemStack stack = new ItemStack(this);
        var modifiers = getAttributeModifiers(EquipmentSlot.MAINHAND, stack);
        var knockback = modifiers.get(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK);
        if (!knockback.isEmpty()) {
            return (int) Objects.requireNonNull(knockback.iterator().next()).getAmount();
        }
        return 0;
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create(); // Veränderbare Multimap
        modifiers.putAll(super.getAttributeModifiers(slot, stack)); // Basis-Modifier
        return modifiers;
    }

    public float getDamage() {
        return this.getAttackDamage();
    }
}
