package com.schokobaer.battleofgods.category.subClass;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.SubClassMethods;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.tier.Tier;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class TerrariaBow extends BowItem implements SubClassMethods {
    /**
     * UUID muss konstant sein, damit Modifier nicht mehrfach stapeln
     */
    public static final UUID SPEED_MODIFIER_UUID = UUID.fromString("5f47c8e2-1f56-4e1a-9e8c-3b2c4d5e6f7a");
    /**
     * +200% MovementSpeed, neutralisiert den Vanilla-Bow-Slowdown
     */
    public static final AttributeModifier SPEED_MODIFIER =
            new AttributeModifier(SPEED_MODIFIER_UUID, "bow_speed_neutralizer", 2.0, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private final Supplier<AbstractSubClass> subClass;
    private int baseDamage;
    private float velocity;
    private int useTime;
    private int knockback = 0;
    private boolean autoSwing;
    private int cooldown = 0;
    private int piercing;
    private SoundEvent soundEvent = SoundEvents.ARROW_SHOOT;


    /**
     * Abstract class for all BattleOfGods bows
     *
     * @param baseDamage Base damage output of bows; damage will be added to arrow damage
     * @param velocity   Speed of the arrow
     * @param useTime    Time it takes for every shot
     * @param knockback  Knockback of the bow
     * @param autoSwing  If the bow is semi- or fully automatic
     * @param piercing   Level of piercing of the bow
     * @param rarity     Rarity of the bow
     * @param tier       Tier of the bow
     */
    public TerrariaBow(int baseDamage, float velocity, int useTime, int knockback, boolean autoSwing, int piercing, RegistryObject<Rarity> rarity, RegistryObject<Tier> tier) {
        super(new Properties().durability(0).defaultDurability(0).setNoRepair());

        this.baseDamage = baseDamage;
        this.velocity = velocity;
        this.useTime = useTime;
        this.knockback = knockback;
        this.autoSwing = autoSwing;
        this.piercing = piercing;

        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.RANGED);
            sb.setRarity(rarity.get());
            sb.setTier(tier.get());
            return sb;
        };
    }
    // TODO :  kein AutoSwing; Arrow Pickup/etc.

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        if (timeLeft <= 1) {
            fireArrow(stack, level, player, InteractionHand.MAIN_HAND, 1f);
        }
    }


    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (cooldown > 0) cooldown--;
    }


    @Override
    public int getUseDuration(ItemStack stack) {
        return useTime;
    }


    protected void fireArrow(ItemStack stack, Level level, Player player, InteractionHand hand, float power) {
/*
        if (BattleOfGods.isDebug()) {
            BattleOfGods.LOGGER.debug("Bow {} fired with power: {}", stack.getItem(), power);
        }
*/
        AbstractArrow arrow = createArrow(level, player, stack);
        if (arrow == null) {
            BattleOfGods.LOGGER.error("Fehler: Pfeil konnte nicht erstellt werden.");
            return;
        }

        arrow = customizeArrow(arrow, power);

        float velocityMultiplier = this.getVelocity() * 3.0f;
        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, velocityMultiplier * power, 1.0f);

        if (!level.isClientSide) {
            level.addFreshEntity(arrow);
            //BattleOfGods.LOGGER.debug("Pfeil wurde erfolgreich gespawnt: {}", arrow);
            stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
        } else {
            BattleOfGods.LOGGER.debug("Client-Seite: Pfeil wird nicht gespawnt.");
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(), this.getSoundEvent(), player.getSoundSource(), 1.0f, 1.0f);

        cooldown = this.useTime; // Cooldown setzen
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public void setSoundEvent(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    protected AbstractArrow createArrow(Level level, LivingEntity shooter, ItemStack bowStack) {
        ArrowItem arrowItem = (ArrowItem) (shooter instanceof Player p ? p.getProjectile(bowStack).getItem() : Items.ARROW);
        AbstractArrow arrow = arrowItem.createArrow(level, bowStack, shooter);

        // Piercing implementieren
        arrow.setPierceLevel((byte) this.getPiercing());

        return arrow;
    }

    protected AbstractArrow customizeArrow(AbstractArrow arrow, float drawProgress) {
        // Terraria-Damage + Arrow-Damage
        double totalDamage = this.getBaseDamage() + arrow.getBaseDamage();

        arrow.setBaseDamage(totalDamage);
        arrow.setKnockback(this.getKnockback());

        // Velocity-Anpassung
        //arrow.setDeltaMovement(arrow.getDeltaMovement().scale(this.velocity / 3.0f));

        return arrow;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(UUID.fromString("8b9b1e3e-8e1a-4d86-81a1-2b5b0d9d9d9d"),
                            "ranged_damage_bonus",
                            this.getBaseDamage(),
                            AttributeModifier.Operation.ADDITION));
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

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public int getKnockback() {
        return knockback;
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    public boolean isAutoSwing() {
        return autoSwing;
    }

    public void setAutoSwing(boolean autoSwing) {
        this.autoSwing = autoSwing;
    }

    public int getPiercing() {
        return piercing;
    }

    public void setPiercing(int piercing) {
        this.piercing = piercing;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }


}