package com.schokobaer.battleofgods.tag;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.classes.MainClass;
import com.schokobaer.battleofgods.init.InitItem;
import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.override.ItemOverride;
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
        BattleofgodsMod.LOGGER.info("Gathering main class data for {}", BattleofgodsMod.MODID);
        try{
            for (RegistryObject<Item> item: InitItem.ITEMS.getEntries()){

                for (RegistryObject<MainClass> mainClass: InitMainClass.MAIN_CLASSES.getEntries()){
                    if (mainClass.get().equals(((ItemOverride) item.get()).getSubClassMethods().getMainClass())){
                        tag(mainClass.get().getTag()).addOptional(TagEntry.element(item.getId()).getId());
                        BattleofgodsMod.LOGGER.debug("Adding item {} to tag {} for {}", item.getId(), mainClass.get().getTag().toString(), BattleofgodsMod.MODID);
                    }
                }
            }
        } catch (Exception e) {
            BattleofgodsMod.LOGGER.error("Error while gathering data for {}", BattleofgodsMod.MODID, e);
        }
    }
}
