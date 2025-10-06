package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.utils.rarity.Rarities;
import com.schokobaer.battleofgods.utils.subClass.TerrariaBow;
import com.schokobaer.battleofgods.utils.tier.GameTiers;

public class ItemCopperBow extends TerrariaBow {
    public ItemCopperBow() {
        super(
                6,
                6.6f,
                29,
                0,
                false,
                0,
                Rarities.WHITE,
                GameTiers.TIER_1
        );
    }
}
