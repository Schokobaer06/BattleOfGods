package com.schokobaer.battleofgods.category.subClass;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.SubClassMethods;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.tier.Tier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class TerrariaBow extends BowItem implements SubClassMethods {
    /// Variablen sind declared, aber wir werden sie vorerst nicht verwenden
    private final int baseDamage;
    private final float velocity;
    private final int useTime;
    private final int knockback;
    private final boolean autoSwing;
    private final Supplier<AbstractSubClass> subClass;
    private int autoSwingDelay = 0;

    public TerrariaBow(int baseDamage, float velocity, int useTime, int knockback, boolean autoSwing,
                       RegistryObject<Rarity> rarity, RegistryObject<Tier> tier) {
        super(new Properties()
                .durability(0)
                .defaultDurability(0)
                .setNoRepair()
        );

        this.baseDamage = baseDamage;
        this.velocity = velocity;
        this.useTime = useTime;
        this.knockback = knockback;
        this.autoSwing = autoSwing;

        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.RANGED);
            sb.setRarity(rarity.get());
            sb.setTier(tier.get());
            return sb;
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                    UUID.fromString("8b9b1e3e-8e1a-4d86-81a1-2b5b0d9d9d9d"),
                    "ranged_damage_bonus",
                    this.baseDamage,
                    AttributeModifier.Operation.ADDITION
            ));
        }
        return modifiers;
    }

    // SubClassMethods Implementierung
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        subClass.get().appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public Component getName(ItemStack stack) {
        return subClass.get().getName(super.getName(stack));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public float getVelocity() {
        return velocity;
    }

    public int getKnockback() {
        return knockback;
    }
}