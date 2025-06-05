package com.schokobaer.battleofgods.category;


import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.mainClass.MainClass;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarities;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.subClass.TerrariaBow;
import com.schokobaer.battleofgods.category.tier.GameTier;
import com.schokobaer.battleofgods.category.tier.GameTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings({"deprecation", "unused"})
public abstract class AbstractSubClass {
    private MainClass mainClass = MainClasses.MISC;
    private Rarity rarity = Rarities.WHITE.getRarity();
    private GameTier tier = GameTiers.TIER_1;


    public AbstractSubClass() {
    }

    public static Tier getTier(
            Tier tier,
            final int enchantmentValue,
            final float destroySpeed,
            final TagKey<Block> blockTag
    ) {
        return new Tier() {
            @Override
            public int getUses() {
                // Terraria‐Style: unzerstörbar → entweder 0 oder Integer.MAX_VALUE.
                return 0;
            }

            @Override
            public float getSpeed() {
                return destroySpeed;
            }

            @Override
            public float getAttackDamageBonus() {
                // Terraria‐Style: kein extra Bonus aus dem Tier selbst
                return 0f;
            }

            @Override
            public int getLevel() {
                // Fallback für Block‐Drops, falls TierSortingRegistry nicht greift
                return tier.getLevel();
            }

            @Override
            public int getEnchantmentValue() {
                return enchantmentValue;
            }

            @Override
            public net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> getTag() {
                return blockTag;
            }

            @Override
            public net.minecraft.world.item.crafting.Ingredient getRepairIngredient() {
                // Terraria‐Style: keine Reparatur
                return Ingredient.EMPTY;
            }
        };
    }

    public static float getDestroySpeedFromMiningSpeed(int miningSpeed) {
        float hitsPerSecond = 60f / (float) miningSpeed;
        float factor = 1.3f;
        return hitsPerSecond * factor;
    }

    public static float getAttackSpeedFromUseTime(int useTime) {
        if (useTime <= 0) {
            BattleOfGods.LOGGER.warn("UseTime must be greater than 0, received: {}", useTime);
            return 1f;
        }
        return 20.0f / useTime;
    }

    public static @NotNull String getKnockback(double knockback, Item item) {
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

    public static @NotNull String getSpeed(double attackSpeed, Item item) {
        // Terraria-UseTime aus Minecraft-APS errechnen
        int useTime;
        if (item instanceof TerrariaBow bow)
            useTime = bow.getUseTime();
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

    public static MainClass getMainClass(Item item) {
        if (item instanceof SwordItem) {
            return MainClasses.MELEE;
        } else if (item instanceof BowItem) {
            return MainClasses.RANGED;
        } else if (item instanceof ArmorItem) {
            return MainClasses.ARMOR;
        } else {
            return MainClasses.MISC;
        }
    }

    public MainClass getMainClass() {
        if (mainClass != null) return this.mainClass;
        else return MainClasses.MISC;
    }

    public void setMainClass(MainClass mainClass) {
        if (mainClass != null) this.mainClass = mainClass;
        else this.mainClass = MainClasses.MISC;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        if (rarity != null) this.rarity = rarity;
        else this.rarity = Rarities.WHITE.getRarity();
    }

    public GameTier getGameTier() {
        return tier;
    }

    public void setGameTier(GameTier tier) {
        if (tier != null) this.tier = tier;
        else this.tier = GameTiers.TIER_1;
    }

    public Component getName(Component name) {
        return name.copy().withStyle(Style.EMPTY.withColor(this.getRarity().getColor()));
    }

    public static Style getStyle(){
        return Style.EMPTY.withColor(ChatFormatting.DARK_GREEN);
    }


    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level level, List<net.minecraft.network.chat.Component> tooltip, TooltipFlag flag) {
        if (itemstack.getItem().getClass().getSuperclass() == null && !(SubClassMethods.class.isAssignableFrom(itemstack.getItem().getClass().getSuperclass()))) return;
        SubClassMethods subClassItem = (SubClassMethods) itemstack.getItem().getClass().getSuperclass().cast(itemstack.getItem());

        ///Rarity
        tooltip.add(1, Component.translatable("rarity.battleofgods." +
                this.getRarity().getDisplayName().toLowerCase()).setStyle(
                Style.EMPTY.withBold(true)
                        .withColor(this.getRarity().getColor())
                        .withItalic(true)
        ));

        /// Damage
        float damage = subClassItem.getDamage();
        String damageText = (damage % 1 == 0)
                ? String.valueOf((int) damage) // If damage is a whole number, show as integer
                : String.format("%.1f", damage); // Otherwise, show with one decimal place
        tooltip.add(2, Component.literal(damageText + " " + this.getMainClass().getName() + " ")
                .append(Component.translatable("tooltip.battleofgods.damage"))
                .withStyle(getStyle()));

        /// Speed
        // Get the attack speed attribute (default to 4.0 if missing)
        double attackSpeed = itemstack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(Attributes.ATTACK_SPEED).stream()
                .mapToDouble(AttributeModifier::getAmount)
                .sum() + 4; // +4.0 because attack speed is offset in Minecraft
        //Convert to Terraria useTime
        String speedText = getSpeed(attackSpeed, itemstack.getItem());

        tooltip.add(3, Component.translatable("tooltip.battleofgods." + speedText)
                .withStyle(AbstractSubClass.getStyle()));

        /// Knockback
        double knockback = subClassItem.getKnockback();
        // Convert to Terraria Knockback
        String kbText = getKnockback(knockback, itemstack.getItem());
        tooltip.add(4, Component.translatable("tooltip.battleofgods." + kbText)
                .withStyle(getStyle()));

        /// Autoswing
        boolean autoSwing = subClassItem.isAutoSwing();
        Component componentAutoSwing = (autoSwing)
                ? Component.literal("✔").withStyle(ChatFormatting.GREEN)
                : Component.literal("✘").withStyle(ChatFormatting.RED);
        tooltip.add(5, Component.translatable("tooltip.battleofgods.autoswing")
                .withStyle(getStyle())
                .append(" ")
                .append(componentAutoSwing));
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

    public static class asTiers implements Tier {


        @Override
        public int getUses() {
            return 0;
        }

        @Override
        public float getSpeed() {
            return 0;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }
    }

}
