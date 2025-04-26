
package com.schokobaer.battleofgods.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

@Deprecated
public class ItemTier1Item extends Item {
	public ItemTier1Item() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
