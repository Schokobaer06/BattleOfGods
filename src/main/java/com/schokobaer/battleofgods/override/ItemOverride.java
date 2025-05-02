package com.schokobaer.battleofgods.override;


import com.schokobaer.battleofgods.classes.AbstractSubClass;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ItemOverride extends net.minecraft.world.item.Item {
    private final AbstractSubClass subClass;
    public ItemOverride(ItemOverride.Properties properties, AbstractSubClass subClass) {
        super(properties);
        this.subClass = subClass;
    }

    public AbstractSubClass getSubClassMethods(){
        return subClass;
    }
    @Override
    public Component getName(ItemStack stack) {
        return getSubClassMethods().getName(super.getName(stack));
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, tooltip, flag);
        getSubClassMethods().appendHoverText(itemstack, level, tooltip, flag);
    }

    @Override
    public boolean isRepairable(@NotNull ItemStack itemstack) {
        return getSubClassMethods().isRepairable(itemstack);
    }
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return getSubClassMethods().getCraftingRemainingItem(stack);
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
