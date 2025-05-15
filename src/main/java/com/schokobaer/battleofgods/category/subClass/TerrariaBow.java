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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class TerrariaBow extends BowItem implements SubClassMethods {

    private final Supplier<AbstractSubClass> subClass;
    private int baseDamage;
    private float velocity;
    private int useTime;
    private int knockback;
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

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
        if (!(entity instanceof Player player)) return;
        // Semi Auto
        if (!isAutoSwing() && timeLeft == (getUseDuration(stack) - getUseTime()))
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_PLING.get() , player.getSoundSource(), 1.0f, 1.0f);

        // Full Auto
        if (timeLeft <=1 && isAutoSwing()) {
            fireArrow(stack, level, player, player.getUsedItemHand(), 1f);
        }
    }


    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;
        // Semi Auto
        if (!isAutoSwing() && timeLeft <= (getUseDuration(stack) - getUseTime())){
            //float power = (float) (this.getUseDuration(stack) - timeLeft) / (float) this.getUseDuration(stack);
            fireArrow(stack, level, player, player.getUsedItemHand(), 1f);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return (!isAutoSwing()) ? super.getUseDuration(stack) : getUseTime();
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public void setSoundEvent(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    protected void fireArrow(ItemStack stack, Level level, Player player, InteractionHand hand, float power) {
        AbstractArrow arrow = createArrow(level, player, stack);
        if (arrow == null) {
            BattleOfGods.LOGGER.error("Error: Arrow is null");
            return;
        }

        arrow = customizeArrow(arrow);

        // Setze den Schaden vor dem Aufruf von shootFromRotation
        arrow.setBaseDamage(this.getBaseDamage() / (this.getVelocity() * power * 3f));

        // Schieße den Pfeil
        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, this.getVelocity() * power * 3f, 1.0f);

        // Setze den Schaden zurück auf den Basiswert
        arrow.setBaseDamage(this.getBaseDamage());

        if (!level.isClientSide) {
            level.addFreshEntity(arrow);
            stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
        }

        // Pfeilverbrauch (außer bei Infinity)
        boolean hasInfinity = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
        if (!hasInfinity && !player.getAbilities().instabuild) {
            ItemStack arrowStack = player.getProjectile(stack);
            if (!arrowStack.isEmpty()) {
                arrowStack.shrink(1);
                if (arrowStack.isEmpty()) {
                    player.getInventory().removeItem(arrowStack);
                }
            }
        }
        if (BattleOfGods.isDebug())
            BattleOfGods.LOGGER.debug("Arrow fired: {} | {} | {} | {}", arrow.getUUID(), arrow.getPierceLevel(), arrow.getBaseDamage(), arrow.getKnockback());
        level.playSound(null, player.getX(), player.getY(), player.getZ(), this.getSoundEvent(), player.getSoundSource(), 1.0f, 1.0f);
    }

    protected AbstractArrow createArrow(Level level, LivingEntity shooter, ItemStack bowStack) {
        if (!(shooter instanceof Player player)) return null;

        ItemStack arrowStack = player.getProjectile(bowStack);
        if (arrowStack.isEmpty()) {
            arrowStack = new ItemStack(Items.ARROW);
        }

        ArrowItem arrowItem = (ArrowItem) (arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);
        AbstractArrow arrow = arrowItem.createArrow(level, arrowStack, shooter);

        // Piercing implementieren
        arrow.setPierceLevel((byte) (arrow.getPierceLevel() + (byte) this.getPiercing()));

        // Pickup-Logik
        boolean isCreative = player.getAbilities().instabuild;
        if (isCreative || arrowStack.is(Items.SPECTRAL_ARROW) || arrowStack.is(Items.TIPPED_ARROW)) {
            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        } else {
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        }

        return arrow;
    }

    protected AbstractArrow customizeArrow(AbstractArrow arrow) {
        // Terraria-Damage + Arrow-Damage
        double totalDamage = this.getBaseDamage() + arrow.getBaseDamage();
        int knockback = this.getKnockback() + arrow.getKnockback();

        // Überprüfen, ob der Besitzer des Pfeils eine LivingEntity ist
        if (arrow.getOwner() instanceof LivingEntity owner) {
            ItemStack ownerItem = owner.getMainHandItem(); // Sicherer Zugriff auf MainHandItem

            // Power-Verzauberung
            int powerLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, ownerItem);
            if (powerLevel > 0) {
                totalDamage += powerLevel * 0.5 + 0.5;
            }

            // Punch-Verzauberung
            int punchLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, ownerItem);
            if (punchLevel > 0) {
                knockback += punchLevel;
            }

            // Flame-Verzauberung
            if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FLAMING_ARROWS, ownerItem) > 0) {
                arrow.setSecondsOnFire(100);
            }
        }


        arrow.setBaseDamage(totalDamage);
        arrow.setKnockback(knockback);
        return arrow;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return LinkedHashMultimap.create();
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