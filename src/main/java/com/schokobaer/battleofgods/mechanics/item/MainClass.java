package com.schokobaer.battleofgods.mechanics.item;

import com.schokobaer.battleofgods.BattleofgodsMod;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.RegistryObject;

import static com.schokobaer.battleofgods.init.InitMainClass.*;

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
