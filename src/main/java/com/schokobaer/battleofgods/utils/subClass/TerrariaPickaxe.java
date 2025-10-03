package com.schokobaer.battleofgods.utils.subClass;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.utils.AbstractSubClass;
import com.schokobaer.battleofgods.utils.SubClassMethods;
import com.schokobaer.battleofgods.utils.mainClass.MainClass;
import com.schokobaer.battleofgods.utils.mainClass.MainClasses;
import com.schokobaer.battleofgods.utils.rarity.Rarities;
import com.schokobaer.battleofgods.utils.rarity.Rarity;
import com.schokobaer.battleofgods.utils.tier.GameTier;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.UUID;

public class TerrariaPickaxe extends PickaxeItem implements SubClassMethods {
    private final int miningSpeed;
    private final double knockback;
    private final int pickaxePower;
    private final AbstractSubClass subClass = new AbstractSubClass() {
    };

    public TerrariaPickaxe(Tier tier, int miningSpeed, int pickaxePower, int attackDamage, float attackSpeed, double knockback, Rarities rarity, GameTier gameTier, TagKey<Block> blocktag) {
        super(AbstractSubClass.getTier(tier, rarity.getEnchantmentLevel(), AbstractSubClass.getDestroySpeedFromMiningSpeed(miningSpeed), blocktag), attackDamage, attackSpeed, new Properties()
                .durability(0)
                .defaultDurability(0)
                .setNoRepair()
                .rarity(rarity.asMinecraftRarity())
        );
        this.subClass.setMainClass(MainClasses.TOOL);
        this.knockback = knockback;
        this.subClass.setRarity(rarity.getRarity());
        this.subClass.setGameTier(gameTier);
        this.miningSpeed = miningSpeed;
        this.pickaxePower = pickaxePower;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return super.hasCraftingRemainingItem(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        subClass.appendHoverText(itemstack, level, tooltip, flag);
        super.appendHoverText(itemstack, level, tooltip, flag);
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
        return (int) this.knockback;
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

    public float getDamage() {
        return this.getAttackDamage();
    }

    public int getPickaxePower() {
        return this.pickaxePower;
    }
}
