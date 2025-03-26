package com.schokobaer.battleofgods.mechanics.item.override;

import com.schokobaer.battleofgods.mechanics.item.AbstractSubClass;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class TieredItemOverride extends ItemOverride {
    private final Tier tier;

    public TieredItemOverride(Tier p_43308_, ItemOverride.Properties p_43309_, AbstractSubClass subClass) {
        super(p_43309_.defaultDurability(p_43308_.getUses()), subClass);
        this.tier = p_43308_;
    }

    public Tier getTier() {
        return this.tier;
    }

    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    public boolean isValidRepairItem(ItemStack p_43311_, ItemStack p_43312_) {
        return this.tier.getRepairIngredient().test(p_43312_) || super.isValidRepairItem(p_43311_, p_43312_);
    }
}
