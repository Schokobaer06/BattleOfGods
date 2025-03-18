package com.schokobaer.battleofgods.mechanics.item;
import com.schokobaer.battleofgods.init.InitItemClass;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class Broadsword extends ItemSubClass{
    private final ItemClass itemClass;
    private final String displayName;
    protected SwordItem swordItem;


    public Broadsword(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, Rarity rarity) {
        super(properties, InitItemClass.MELEE.get(), "Broadsword",  InitItemClass.MELEE.get().getTag());
        this.itemClass = InitItemClass.MELEE.get();
        this.displayName = "Broadsword";
        this.swordItem = new SwordItem(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.setRarity(rarity);
    }

    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return swordItem.hasCraftingRemainingItem(stack);
    }

    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return swordItem.getCraftingRemainingItem(itemstack);
    }
}

