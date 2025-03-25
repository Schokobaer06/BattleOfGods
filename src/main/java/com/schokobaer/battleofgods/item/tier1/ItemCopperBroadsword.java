package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemCopperBroadsword extends Broadsword {
    public ItemCopperBroadsword() {
        super(0, 0,Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:ingots/copper"))), 2, 0, new Properties(), InitRarity.WHITE, InitTier.TIER_1);
    }
}