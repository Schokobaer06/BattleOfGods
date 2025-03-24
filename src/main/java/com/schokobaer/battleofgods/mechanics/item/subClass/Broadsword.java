package com.schokobaer.battleofgods.mechanics.item.subClass;

import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.mechanics.item.AbstractSubClass;

import com.schokobaer.battleofgods.mechanics.item.SubClassMethods;
import com.schokobaer.battleofgods.mechanics.item.override.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
//import net.minecraft.world.item.*;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Broadsword extends com.schokobaer.battleofgods.mechanics.item.override.SwordItem {
    public Broadsword(float attackDamageBonus, int enchantmentValue, Ingredient repairMaterial, int attackDamage, float attackSpeed, Properties properties, RegistryObject<com.schokobaer.battleofgods.mechanics.rarity.Rarity> rarity, RegistryObject<com.schokobaer.battleofgods.mechanics.tier.Tier> gameTier) {
        super(new Tier() {
            @Override
            public int getUses() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 1.6f;
            }

            @Override
            public float getAttackDamageBonus() {
                return attackDamageBonus;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return enchantmentValue;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return repairMaterial;
            }
        }, attackDamage, attackSpeed, properties, new AbstractSubClass(InitMainClass.MELEE,rarity,gameTier,"broadsword") {});
    }
    public Broadsword(Tier tier, int attackDamage, float attackSpeed, Properties properties, RegistryObject<com.schokobaer.battleofgods.mechanics.rarity.Rarity> rarity, RegistryObject<com.schokobaer.battleofgods.mechanics.tier.Tier> gameTier) {
        super(tier, attackDamage, attackSpeed, properties, new AbstractSubClass(InitMainClass.MELEE,rarity,gameTier,"broadsword"){});}

    public Broadsword(TagKey<Item> tag){
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
        }, 0, 0, new Properties(), new AbstractSubClass("broadsword", InitMainClass.MELEE,tag){});
    }



    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return super.hasCraftingRemainingItem(stack);
    }

}

