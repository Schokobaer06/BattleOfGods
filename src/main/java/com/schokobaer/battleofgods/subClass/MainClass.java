package com.schokobaer.battleofgods.subClass;

import net.minecraft.tags.TagKey;

public class MainClass {
    private final String name;
    private TagKey<MainClass> tag;

    public MainClass(String name){
        this.name = name;
    }
    public MainClass(String name, TagKey<MainClass> tag){
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public TagKey<MainClass> getTag() {
        return this.tag;
    }
}
