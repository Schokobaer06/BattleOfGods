package com.schokobaer.battleofgods.mechanics.tag;// ModItemTagsProvider.java

import com.schokobaer.battleofgods.init.*;
import com.schokobaer.battleofgods.mechanics.item.MainClass;
import com.schokobaer.battleofgods.mechanics.item.override.Item;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import com.schokobaer.battleofgods.mechanics.tier.Tier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
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

            // goes through every registered MainClass
            for (RegistryObject<MainClass> mainClass : InitMainClass.MAIN_CLASSES.getEntries()) {

                // goes through every registered SubClass
                for (RegistryObject<Item> subClass : InitSubClass.SUBCLASSES.getEntries()){
                   //Item subClassItem = subClass.get();

                   //Check if Item belongs to subClass
                   if (item.getSubClassMethods().getSubClass() == subClass.get()){
                       tag(subClass.get().getSubClassMethods().getTag()).addOptional(registryObject.getId());
                   }
                   //Check if subClass belongs to mainClass
                   if (subClass.get().getSubClassMethods().getMainClass() == mainClass.get()){
                        tag(mainClass.get().getTag()).addOptionalTag(subClass.get().getSubClassMethods().getTag());
                   }
                }
            }

            // goes through every registered Rarity
            for (RegistryObject<Rarity> rarity : InitRarity.RARITIES.getEntries()) {

                //Check if item belongs to rarity
                if (item.getSubClassMethods().getRarity() == rarity.get()){
                    tag(rarity.get().getTag()).addOptional(registryObject.getId());
                }
            }
            // goes through every registered Tier
            for (RegistryObject<Tier> tier : InitTier.TIERS.getEntries()) {

                //Check if item belongs to tier
                if(item.getSubClassMethods().getTier() == tier.get()){
                    tag(tier.get().getTag()).addOptional(registryObject.getId());
                }
            }


        }
    }

}

