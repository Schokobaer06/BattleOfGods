package com.schokobaer.battleofgods.mechanics.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface SubClassMethods{
    public Component getName(ItemStack stack);
    public void appendHoverText(ItemStack itemstack, Level level, List<net.minecraft.network.chat.Component> tooltip, TooltipFlag flag);
    public boolean hasCraftingRemainingItem(ItemStack stack);
    public ItemStack getCraftingRemainingItem(ItemStack itemstack);
    public boolean isRepairable(@NotNull ItemStack itemstack);
}
