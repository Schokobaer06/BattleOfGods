package com.schokobaer.battleofgods.mechanics.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

import javax.swing.text.html.HTML;

public class Broadsword extends ItemSubClass{
    private final ItemClass itemClass;
    private final String displayName;
    private SwordItem swordItem;


    public Broadsword(Properties properties, ItemClass itemClass, String displayName, TagKey<Item> tag) {
        super(properties, itemClass, displayName, tag);
        this.itemClass = itemClass;
        this.displayName = displayName;
    }


}
