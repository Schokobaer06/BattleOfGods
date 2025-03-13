
package com.schokobaer.battleofgods.item;

import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CopperShortswordItem extends SwordItem {
	private final Rarity rarity;
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
		this.rarity = new Rarity(0xFFA500);
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
	public Component getName(ItemStack stack) {
		MutableComponent name = Component.translatable(getDescriptionId(stack));

		// Farbe basierend auf der Rarität anwenden
		int color = rarity.getArgbColor();
		return name.withStyle(Style.EMPTY.withColor(color));
	}
	@Override
	public boolean isRepairable(@NotNull ItemStack itemstack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.literal("Rarity: ").setStyle(Style.EMPTY.withColor(rarity.getArgbColor())));
	}
}
