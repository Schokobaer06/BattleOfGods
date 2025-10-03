package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.utils.rarity.Rarities;
import com.schokobaer.battleofgods.utils.subClass.TerrariaBow;
import com.schokobaer.battleofgods.utils.tier.GameTiers;

public class ItemWoodBow extends TerrariaBow {

    public ItemWoodBow() {
        super(
                4,
                1,
                30,
                0,
                false,
                0,
                Rarities.WHITE,
                GameTiers.TIER_1
        );
    }
}
