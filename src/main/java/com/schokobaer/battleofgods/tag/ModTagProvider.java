package com.schokobaer.battleofgods.tag;// ModItemTagsProvider.java


import com.schokobaer.battleofgods.classes.MainClass;
import com.schokobaer.battleofgods.init.*;
import com.schokobaer.battleofgods.override.ItemOverride;
import com.schokobaer.battleofgods.rarity.Rarity;
import com.schokobaer.battleofgods.tier.Tier;
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


@Deprecated
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
        for (RegistryObject<net.minecraft.world.item.Item> registryObject : InitItem.ITEMS.getEntries()) {
            ItemOverride item = (ItemOverride) registryObject.get();

            // goes through every registered MainClass
            for (RegistryObject<MainClass> mainClass : InitMainClass.MAIN_CLASSES.getEntries()) {

                // goes through every registered SubClass
                for (RegistryObject<Item> subClass : InitSubClass.SUBCLASSES.getEntries()){
                   ItemOverride subClassItem = (ItemOverride) subClass.get();

                   //Check if Item belongs to subClass
                   if (item.getSubClassMethods().getSubClass().equals(subClass.get())){
                       tag(subClassItem.getSubClassMethods().getTag()).addOptional(registryObject.getId());
                   }
                   //Check if subClass belongs to mainClass
                   if (subClassItem.getSubClassMethods().getMainClass().equals(mainClass.get())){
                        tag(mainClass.get().getTag()).addOptionalTag(subClass.getId());
                   }
                }
            }

            // goes through every registered Rarity
            for (RegistryObject<Rarity> rarity : InitRarity.RARITIES.getEntries()) {

                //Check if item belongs to rarity
                if (item.getSubClassMethods().getRarity().equals(rarity.get())){
                    tag(rarity.get().getTag()).addOptional(registryObject.getId());
                }
            }
            // goes through every registered Tier
            for (RegistryObject<Tier> tier : InitTier.TIERS.getEntries()) {

                //Check if item belongs to tier
                if(item.getSubClassMethods().getTier().equals(tier.get())){
                    tag(tier.get().getTag()).addOptional(registryObject.getId());
                }
            }


        }
    }

}

