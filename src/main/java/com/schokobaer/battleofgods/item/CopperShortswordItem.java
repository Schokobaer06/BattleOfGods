
package com.schokobaer.battleofgods.item;

import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CopperShortswordItem extends SwordItem {
		public CopperShortswordItem() {
		super(new Tier() {
			public int getUses() {
				return 100;
			}

			public float getSpeed() {
				return 2f;
			}

			public float getAttackDamageBonus() {
				return 1f;
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
		}, 3, -1f, new Item.Properties());
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		ItemStack retval = new ItemStack(this);
		retval.setDamageValue(itemstack.getDamageValue() + 1);
		if (retval.getDamageValue() >= retval.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return retval;
	}

	@Override
	public boolean isRepairable(@NotNull ItemStack itemstack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);

		list.add(Component.literal(InitRarity.RAINBOW.get().getDisplayName()).withStyle(Style.EMPTY.withColor(InitRarity.RAINBOW.get().getArgbColor())));
		list.add(Component.literal(InitRarity.FIERY_RED.get().getDisplayName()).withStyle(Style.EMPTY.withColor(InitRarity.FIERY_RED.get().getArgbColor())));
		list.add(Component.literal(InitRarity.TEAL.get().getDisplayName()).withStyle(Style.EMPTY.withColor(InitRarity.TEAL.get().getArgbColor())));

		//list.add(Component.literal("Legendary").withStyle(Style.EMPTY.withColor(new Rarity("Legendary", new ResourceLocation("battleofgods:textures/rarity/rainbow.png")).getArgbColor())));
		//list.add(Component.literal(InitRarity.GRAY.get().getDisplayName()).withStyle(Style.EMPTY.withColor(InitRarity.GRAY.get().getArgbColor())));

	}
}
