package com.schokobaer.battleofgods.mechanics.item;

public class Broadsword extends ItemClass{
    private final ItemClass itemClass;
    private final String displayName;

    /**
     * Included all Items that are Broadswords
     * @param properties
     * @param itemClass
     * @param displayName
     */
    public Broadsword(Properties properties, ItemClass itemClass, String displayName) {
        super(properties, itemClass.getName());
        this.itemClass = itemClass;
        this.displayName = displayName;
    }
}
