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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class TerrariaBow extends BowItem implements SubClassMethods {
    private final int baseDamage;
    private final float velocity;
    private final int useTime;
    private final int knockback;
    private final Supplier<AbstractSubClass> subClass;

    public TerrariaBow(int baseDamage, float velocity, int useTime, int knockback,
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
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        float drawProgress = getPowerForTime(this.getUseDuration(stack) - timeLeft);
        if (drawProgress < 0.1) return;

        // Arrow Handling
        AbstractArrow arrow = createArrow(level, entity, stack);
        arrow = customizeArrow(arrow, drawProgress);

        arrow.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F,
                this.velocity * 3.0F * drawProgress, 1.0F);

        stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));
        level.addFreshEntity(arrow);
    }

    protected AbstractArrow createArrow(Level level, LivingEntity shooter, ItemStack bowStack) {
        ArrowItem arrowItem = (ArrowItem) Items.ARROW;
        return arrowItem.createArrow(level, bowStack, shooter);
    }

    protected AbstractArrow customizeArrow(AbstractArrow arrow, float drawProgress) {
        // Damage Calculation: Base Damage + Arrow Damage
        arrow.setBaseDamage(this.baseDamage + arrow.getBaseDamage());

        // Remove Knockback
        arrow.setKnockback(0);

        // Velocity Multiplier
        arrow.setDeltaMovement(arrow.getDeltaMovement().scale(this.velocity / 3.0F));

        return arrow;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return this.useTime; // UseTime in Ticks (20 ticks = 1 second)
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
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