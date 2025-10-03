package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.utils.rarity.Rarities;
import com.schokobaer.battleofgods.utils.subClass.Broadsword;
import com.schokobaer.battleofgods.utils.tier.GameTiers;
import com.schokobaer.battleofgods.utils.tier.Tiers;

public class ItemCopperBroadsword extends Broadsword {
    public ItemCopperBroadsword() {
        super(
                Tiers.GOLD,
                9,
                0.95f,
                1.4,
                false,
                Rarities.WHITE,
                GameTiers.TIER_1
        );
    }
}