package com.schokobaer.battleofgods.mechanics.item.override;


import com.schokobaer.battleofgods.mechanics.item.AbstractSubClass;


public class ItemOverride extends net.minecraft.world.item.Item {
    private final AbstractSubClass subClass;
    public ItemOverride(ItemOverride.Properties p_41383_, AbstractSubClass subClass) {
        super(p_41383_);
        this.subClass = subClass;
    }

    public AbstractSubClass getSubClassMethods(){
        return subClass;
    }
}
