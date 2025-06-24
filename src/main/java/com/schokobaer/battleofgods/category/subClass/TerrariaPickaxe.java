package com.schokobaer.battleofgods.category.subClass;

import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarities;
import com.schokobaer.battleofgods.category.tier.GameTier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

public class TerrariaPickaxe extends PickaxeItem {
    private final AbstractSubClass subClass = new AbstractSubClass() {
    };

    public TerrariaPickaxe(Tier tier, int miningSpeed, int attackDamage, float attackSpeed, Rarities rarity, GameTier gameTier, TagKey<Block> blocktag) {
        super(AbstractSubClass.getTier(tier, rarity.getEnchantmentLevel(), AbstractSubClass.getDestroySpeedFromMiningSpeed(miningSpeed), blocktag), attackDamage, attackSpeed, new Properties()
                .durability(0)
                .defaultDurability(0)
                .setNoRepair()
                .rarity(rarity.asMinecraftRarity())
        );
        this.subClass.setMainClass(MainClasses.TOOL);
        this.subClass.setRarity(rarity.getRarity());
        this.subClass.setGameTier(gameTier);
    }

    //TODO
}
