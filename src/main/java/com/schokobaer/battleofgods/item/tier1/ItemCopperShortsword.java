package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.mechanics.item.subClass.Shortsword;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemCopperShortsword extends Shortsword {
    public ItemCopperShortsword() {
        super(0, 0, Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:ingots/copper"))), 3, 1.7f, new Properties(), InitRarity.WHITE, InitTier.TIER_1);
    }
}
