package com.schokobaer.battleofgods.category.tier;

import com.schokobaer.battleofgods.category.rarity.Rarities;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum Tiers implements Tier {
    GRAY(Rarities.GRAY.getRarity(), 0, 0, 1.0F, 0.0F, 5, () -> Ingredient.EMPTY),
    WHITE(Rarities.WHITE.getRarity(), 1, 0, 1.5F, 0.0F, 8, () -> Ingredient.EMPTY),
    GREEN(Rarities.GREEN.getRarity(), 2, 0, 2.0F, 0.0F, 12, () -> Ingredient.EMPTY),
    BLUE(Rarities.BLUE.getRarity(), 3, 0, 2.5F, 0.0F, 15, () -> Ingredient.EMPTY),
    ORANGE(Rarities.ORANGE.getRarity(), 4, 0, 3.0F, 0.0F, 18, () -> Ingredient.EMPTY),
    LIGHT_RED(Rarities.LIGHT_RED.getRarity(), 5, 0, 3.5F, 0.0F, 20, () -> Ingredient.EMPTY),
    PINK(Rarities.PINK.getRarity(), 6, 0, 4.0F, 0.0F, 22, () -> Ingredient.EMPTY),
    LIGHT_PURPLE(Rarities.LIGHT_PURPLE.getRarity(), 7, 0, 4.5F, 0.0F, 25, () -> Ingredient.EMPTY),
    LIME(Rarities.LIME.getRarity(), 8, 0, 5.0F, 0.0F, 28, () -> Ingredient.EMPTY),
    YELLOW(Rarities.YELLOW.getRarity(), 9, 0, 5.5F, 0.0F, 30, () -> Ingredient.EMPTY),
    CYAN(Rarities.CYAN.getRarity(), 10, 0, 6.0F, 0.0F, 32, () -> Ingredient.EMPTY),
    RED(Rarities.RED.getRarity(), 11, 0, 6.5F, 0.0F, 35, () -> Ingredient.EMPTY),
    PURPLE(Rarities.PURPLE.getRarity(), 12, 0, 7.0F, 0.0F, 38, () -> Ingredient.EMPTY),
    TURQUOISE(Rarities.TURQUOISE.getRarity(), 13, 0, 7.5F, 0.0F, 40, () -> Ingredient.EMPTY),
    PURE_GREEN(Rarities.PURE_GREEN.getRarity(), 14, 0, 8.0F, 0.0F, 42, () -> Ingredient.EMPTY),
    DARK_BLUE(Rarities.DARK_BLUE.getRarity(), 15, 0, 8.5F, 0.0F, 45, () -> Ingredient.EMPTY),
    VIOLET(Rarities.VIOLET.getRarity(), 16, 0, 9.0F, 0.0F, 48, () -> Ingredient.EMPTY),
    HOT_PINK(Rarities.HOT_PINK.getRarity(), 17, 0, 9.5F, 0.0F, 50, () -> Ingredient.EMPTY),
    CALAMITY_RED(Rarities.CALAMITY_RED.getRarity(), 18, 0, 10.0F, 0.0F, 52, () -> Ingredient.EMPTY),
    AMBER(Rarities.AMBER.getRarity(), 19, 0, 10.5F, 0.0F, 55, () -> Ingredient.EMPTY),
    DARK_ORANGE(Rarities.DARK_ORANGE.getRarity(), 20, 0, 11.0F, 0.0F, 58, () -> Ingredient.EMPTY),
    RAINBOW(Rarities.RAINBOW.getRarity(), 21, 0, 11.5F, 0.0F, 60, () -> Ingredient.EMPTY),
    FIERY_RED(Rarities.FIERY_RED.getRarity(), 22, 0, 12.0F, 0.0F, 62, () -> Ingredient.EMPTY),
    TEAL(Rarities.TEAL.getRarity(), 23, 0, 12.5F, 0.0F, 65, () -> Ingredient.EMPTY);

    private final Rarity rarity;
    private final int level;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    Tiers(Rarity rarity, int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.rarity = rarity;
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    public Rarity getRarity() {
        return rarity;
    }


    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamageBonus;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    public static enum MaterialTier implements Tier {

        None();

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

        @Override
        public @Nullable TagKey<Block> getTag() {
            return Tier.super.getTag();
        }
    }

    ;
}
