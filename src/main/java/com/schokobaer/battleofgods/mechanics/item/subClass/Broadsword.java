package com.schokobaer.battleofgods.mechanics.item.subClass;

import com.schokobaer.battleofgods.mechanics.item.ItemClass;
import com.schokobaer.battleofgods.mechanics.item.ItemSubClass;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import net.minecraftforge.registries.RegistryObject;

public class Broadsword extends ItemSubClass {
    protected SwordItem swordItem;
/*
    public Broadsword(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, RegistryObject<Rarity> rarity, RegistryObject<ItemClass> itemClass) {
        super(properties,itemClass, "broadsword");

        //this.swordItem = new SwordItem(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.setRarity(rarity);
    }*/
    public Broadsword(Properties properties, String name, RegistryObject<ItemClass> itemClass, TagKey<Item> tag) {
        super(properties, itemClass, name, tag);

    }

    public void setSwordItem(SwordItem swordItem) {
        this.swordItem = swordItem;
    }

    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return swordItem.hasCraftingRemainingItem(stack);
    }

    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return swordItem.getCraftingRemainingItem(itemstack);
    }
}

