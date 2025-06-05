package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.category.rarity.Rarities;
import com.schokobaer.battleofgods.category.subClass.Broadsword;
import com.schokobaer.battleofgods.category.tier.GameTiers;
import com.schokobaer.battleofgods.category.tier.Tiers;

public class ItemWoodSword extends Broadsword {
    public ItemWoodSword() {
        super(Tiers.GOLD, 7, 1f, 0.5, false, Rarities.WHITE, GameTiers.TIER_1);
    }
}
