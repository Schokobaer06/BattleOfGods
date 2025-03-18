package com.schokobaer.battleofgods.mechanics.item.subClass;
import com.schokobaer.battleofgods.init.InitItemClass;
import com.schokobaer.battleofgods.init.InitItemSubClass;
import com.schokobaer.battleofgods.mechanics.item.ItemSubClass;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class Broadsword extends ItemSubClass {
    protected SwordItem swordItem;

    public Broadsword(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, Rarity rarity) {
        super(properties, InitItemClass.MELEE.get(), "Broadsword",  InitItemSubClass.BROADSWORD.get().getTag());

        this.swordItem = new SwordItem(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.setRarity(rarity);
    }
    public Broadsword(Properties properties, String name, TagKey<Item> tag){
        super(properties, InitItemClass.MELEE.get(), name, tag);
    }

    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return swordItem.hasCraftingRemainingItem(stack);
    }

    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return swordItem.getCraftingRemainingItem(itemstack);
    }
}

