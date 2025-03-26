package com.schokobaer.battleofgods.mechanics.tag;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.init.InitSubClass;
import com.schokobaer.battleofgods.mechanics.item.MainClass;
import com.schokobaer.battleofgods.mechanics.item.override.ItemOverride;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MainClassTagProvider extends TagsProvider<MainClass> {

    public MainClassTagProvider(PackOutput output, ResourceKey<? extends Registry<MainClass>> registryKey,
                               CompletableFuture<HolderLookup.Provider> lookupProvider, String modid,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registryKey, lookupProvider, modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        try {
            for (RegistryObject<Item> subClass : InitSubClass.SUBCLASSES.getEntries()) {

                for (RegistryObject<MainClass> mainClass : InitMainClass.MAIN_CLASSES.getEntries()) {
                    if (((ItemOverride) subClass.get()).getSubClassMethods().getMainClass().equals(mainClass.get())){
                        tag(mainClass.get().getTag()).addOptionalTag(TagEntry.element(subClass.getId()).getId());
                    }
                }
            }
        } catch (Exception e) {
            BattleofgodsMod.LOGGER.error("Error while gathering data for {}", BattleofgodsMod.MODID, e);
        }
    }
}
