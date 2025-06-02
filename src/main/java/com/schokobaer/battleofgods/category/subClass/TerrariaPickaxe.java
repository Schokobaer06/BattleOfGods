package com.schokobaer.battleofgods.category.subClass;

import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.tier.GameTier;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class TerrariaPickaxe extends PickaxeItem {
    private final Supplier<AbstractSubClass> subClass;

    public TerrariaPickaxe(float pickaxePower, int attackDamage, float attackSpeed, Rarity rarity, GameTier gameTier) {
        super(new Tier() {
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
        }, attackDamage, attackSpeed, new Properties()
                .durability(0)
                .defaultDurability(0)
                .setNoRepair()
                .rarity(rarity.asMinecraftRarity()));
        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.MELEE);
            sb.setRarity(rarity);
            sb.setGameTier(gameTier);
            return sb;
        };
    }
}
