package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.category.subClass.TerrariaPickaxe;
import com.schokobaer.battleofgods.category.tier.GameTiers;
import com.schokobaer.battleofgods.category.tier.Tiers;
import net.minecraft.tags.BlockTags;

public class ItemCopperPickaxe extends TerrariaPickaxe {
    public ItemCopperPickaxe() {
        super(Tiers.GOLD,
                15,
                2,
                -2.4f,
                com.schokobaer.battleofgods.category.rarity.Rarities.WHITE,
                GameTiers.TIER_1,
                BlockTags.MINEABLE_WITH_PICKAXE
        );
    }
}
