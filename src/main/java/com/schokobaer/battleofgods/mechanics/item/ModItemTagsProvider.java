package com.schokobaer.battleofgods.mechanics.item;// ModItemTagsProvider.java
import com.schokobaer.battleofgods.init.InitItemClass;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagsProvider.contentsGetter(), "battleofgods", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Automatische Generierung für ItemClass-Tags
        for (RegistryObject<ItemClass> itemClass : InitItemClass.ITEM_CLASSES.getEntries()) {
            tag(itemClass.get().getTag()).add(itemClass.get());
        }
        /*
        // Automatische Generierung für ItemSubclass-Tags
        for (RegistryObject<ItemSubClass> subclass : InitItemSubClass.SUBCLASSES.getEntries()) {
            tag(subclass.get().getTag()).add(InitItems.ITEMS.getEntries().stream()
                    .filter(item -> item.get() instanceof CustomItem)
                    .map(item -> (CustomItem) item.get())
                    .filter(item -> item.getSubclass().equals(subclass.get()))
                    .toArray(Item[]::new));
        }

         */
    }
}