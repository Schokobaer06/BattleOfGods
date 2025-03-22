package com.schokobaer.battleofgods.mechanics.tag;// ModItemTagsProvider.java

import com.schokobaer.battleofgods.init.*;
import com.schokobaer.battleofgods.mechanics.item.AbstractSubClass;
import com.schokobaer.battleofgods.mechanics.item.MainClass;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends TagsProvider {
    //private final ResourceKey<?> registryKey;

    public ModTagProvider(PackOutput output, ResourceKey<? extends Registry<?>> registryKey,
                          CompletableFuture<HolderLookup.Provider> lookupProvider, String modid,
                          @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registryKey, lookupProvider, modid, existingFileHelper);
        //this.registryKey = registryKey;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // goes through every registered Item
        for (RegistryObject<Item> registryObject : InitItem.ITEMS.getEntries()) {
            Item item = registryObject.get();
            // goes through every registered SubClass
            for (RegistryObject<Item> subClass : InitSubClass.SUBCLASSES.getEntries()){
               Item subClassItem = subClass.get();
               //Check if item inherits from subclass
               if (subClassItem.getClass().isAssignableFrom(item.getClass())){
                   //TODO: get tags from a list/map generated from TagCreator
               }
            }

        }
    }

}

