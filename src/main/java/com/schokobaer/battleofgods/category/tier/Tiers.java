package com.schokobaer.battleofgods.category.tier;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum Tiers implements Tier {

    GOLD(2),
    EVIL_ORE(3),
    HELL_STONE(4),
    COBALT(5),
    MYTHRIL(6),
    ADAMANTITE(7),
    CHLOROPHYTE(8),
    LIHZAHRD(9),
    EXODIUM(10),
    AURIC(11);

    private final int harvestLevel;

    Tiers(int harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

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
        return harvestLevel;
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
