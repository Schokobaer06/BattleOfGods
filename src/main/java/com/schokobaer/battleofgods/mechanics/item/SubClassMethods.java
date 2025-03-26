package com.schokobaer.battleofgods.mechanics.item;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface SubClassMethods{
    Component getName(Component name);
    void appendHoverText(ItemStack itemstack, Level level, List<net.minecraft.network.chat.Component> tooltip, TooltipFlag flag);
    boolean hasCraftingRemainingItem(ItemStack stack);

    TagKey<?> getTag();

    ItemStack getCraftingRemainingItem(ItemStack itemstack);
    boolean isRepairable(@NotNull ItemStack itemstack);
}
