package com.schokobaer.battleofgods.mechanics.item.override;


import com.schokobaer.battleofgods.mechanics.item.AbstractSubClass;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemOverride extends net.minecraft.world.item.Item {
    private final AbstractSubClass subClass;
    public ItemOverride(ItemOverride.Properties p_41383_, AbstractSubClass subClass) {
        super(p_41383_);
        this.subClass = subClass;
    }
    public ItemOverride(ItemOverride.Properties p_41383_) {
        super(p_41383_);
        this.subClass = null;
    }

    public AbstractSubClass getSubClassMethods(){
        return subClass;
    }


    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        net.minecraft.world.item.Item item = new net.minecraft.world.item.Item(new Properties());
        return subClass.getCraftingRemainingItem(itemstack);
    }
    @Override
    public Component getName(ItemStack stack) {
        return subClass.getName(stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, tooltip, flag);
        subClass.appendHoverText(itemstack, level, tooltip, flag);
    }
    @Override
    public boolean isRepairable(@NotNull ItemStack itemstack) {
        return subClass.isRepairable(itemstack);
    }
}
