package com.schokobaer.battleofgods.mechanics.tag;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitItem;
import com.schokobaer.battleofgods.init.InitSubClass;
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

public class SubClassTagProvider extends TagsProvider<ItemOverride> {

    public SubClassTagProvider(PackOutput output, ResourceKey<? extends Registry<ItemOverride>> registryKey,
                          CompletableFuture<HolderLookup.Provider> lookupProvider, String modid,
                          @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registryKey, lookupProvider, modid, existingFileHelper);
        //this.registryKey = registryKey;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        for (RegistryObject<Item> item: InitItem.ITEMS.getEntries()){

            for (RegistryObject<Item> subClass: InitSubClass.SUBCLASSES.getEntries()){

                if (item.get() instanceof ItemOverride){
                    if (subClass.get() instanceof ItemOverride){
                        if (subClass.get().equals(((ItemOverride) item.get()).getSubClassMethods().getSubClass())){
                            tag(((ItemOverride) subClass.get()).getSubClassMethods().getTag()).add(TagEntry.element(item.getId()));
                            BattleofgodsMod.LOGGER.info("Adding item {} to tag {} for {}", item.getId(), ((ItemOverride) subClass.get()).getSubClassMethods().getTag().toString(), BattleofgodsMod.MODID);

                        }
                    }
                }
            }
        }
    }
}
