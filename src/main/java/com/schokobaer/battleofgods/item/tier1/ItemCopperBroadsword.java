package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemCopperBroadsword extends Broadsword {
    public ItemCopperBroadsword() {
        super(new Properties(), new Tier() {
            public int getUses() {
                return 0;
            }

            public float getSpeed() {
                return 2f;
            }

            public float getAttackDamageBonus() {
                return 5f;
            }

            public int getLevel() {
                return 0;
            }

            public int getEnchantmentValue() {
                return 15;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:ingots/copper")));
            }
        }, 3, -1.5f, InitRarity.WHITE.get());
    }
}
