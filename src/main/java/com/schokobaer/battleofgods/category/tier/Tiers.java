package com.schokobaer.battleofgods.category.tier;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

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

    //TODO: has to be changed when adding tungsten pickaxe

    static {
        TierSortingRegistry.registerTier(
                Tiers.METEORITE(),
                new ResourceLocation(BattleOfGods.MODID, "meteorite"),
                List.of(new ResourceLocation("iron")),
                List.of(new ResourceLocation("diamond"))
        );
    }

    /// Only used für tungsten pickaxe
    public static Tier METEORITE() {
        return new Tier() {
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
                return 2;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }
        };
    }

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
        return Ingredient.EMPTY;
    }
}
