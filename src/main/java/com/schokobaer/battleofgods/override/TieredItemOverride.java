package com.schokobaer.battleofgods.override;

import com.schokobaer.battleofgods.classes.AbstractSubClass;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class TieredItemOverride extends ItemOverride {
    private final Tier tier;

    public TieredItemOverride(Tier tier, ItemOverride.Properties properties, AbstractSubClass subClass) {
        super(properties.defaultDurability(tier.getUses()), subClass);
        this.tier = tier;
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
