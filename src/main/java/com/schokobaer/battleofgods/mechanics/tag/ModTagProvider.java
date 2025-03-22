package com.schokobaer.battleofgods.mechanics.tag;// ModItemTagsProvider.java

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.*;
import com.schokobaer.battleofgods.item.tier1.ItemCopperBroadsword;
import com.schokobaer.battleofgods.mechanics.item.MainClass;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.Main;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends TagsProvider {
    private final ResourceKey<?> registryKey;

    public ModTagProvider(PackOutput output, ResourceKey<? extends Registry<?>> registryKey,
                          CompletableFuture<HolderLookup.Provider> lookupProvider, String modid,
                          @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registryKey, lookupProvider, modid, existingFileHelper);
        this.registryKey = registryKey;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        if (getRegistryKey() == InitMainClass.MAIN_CLASSES.getRegistryKey()){
            for (RegistryObject<Item> subClass : InitSubClass.SUBCLASSES.getEntries()){
                if (subClass.get() instanceof Broadsword){

                }
            }
        }
        else if (getRegistryKey() == InitSubClass.SUBCLASSES.getRegistryKey()){

        }
        else if (getRegistryKey() == InitRarity.RARITIES.getRegistryKey()){

        }
        else if (getRegistryKey() == InitTier.TIERS.getRegistryKey()){

        }

    }

    private ResourceKey<?> getRegistryKey(){
        return this.registryKey;
    }
}

