package com.schokobaer.battleofgods.mechanics.item;// ModItemTagsProvider.java
import com.schokobaer.battleofgods.init.InitItemClass;
import com.schokobaer.battleofgods.init.InitItemSubClass;
import com.schokobaer.battleofgods.init.InitItems;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.mechanics.tier.Tier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
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

        // Automatische Generierung für ItemSubclass-Tags
        for (RegistryObject<ItemSubClass> subClass : InitItemSubClass.ITEM_SUBCLASSES.getEntries()) {
            tag(subClass.get().getTag())
                    .add(InitItems.ITEMS.getEntries().stream()
                    .filter(item -> item.get() instanceof ItemSubClass)
                    .map(item -> (ItemSubClass) item.get())
                    .filter(item -> item.getSubclass().equals(subClass.get()))
                    .toArray(Item[]::new));
        }

        // Auto Generierung for Tiers - tags

        for (RegistryObject<Tier> tier : InitTier.TIERS.getEntries()) {
            tag(tier.get().getTag())
                    .add(InitItems.ITEMS.getEntries().stream()
                            .filter(item -> item.get() instanceof ItemSubClass)
                            .map(item -> (ItemSubClass) item.get())
                            .filter(item -> item.getTier().equals(tier.get()))
                            .toArray(Item[]::new));
        }
    }
}