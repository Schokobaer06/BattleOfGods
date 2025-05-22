package com.schokobaer.battleofgods.dataGeneration;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.SubClassMethods;
import com.schokobaer.battleofgods.init.InitItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider {


    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> holderLookupCompletableFuture, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, holderLookupCompletableFuture, tagLookupCompletableFuture, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        try {
            for (RegistryObject<Item> entry : InitItem.ITEMS.getEntries()) {
                //BattleOfGods.LOGGER.debug(item.get().getClass().getSuperclass().getSuperclass().toString());
                Item item = entry.get();
                try{
                if (SubClassMethods.class.isAssignableFrom(item.getClass().getSuperclass())) {
                    SubClassMethods subClassMethods = (SubClassMethods) item.getClass().getSuperclass().cast(item);
                    //String tier = subClassMethods.getTier();
                    //BattleOfGods.LOGGER.debug("Die Parentklasse von {} implementiert SubClassMethods mit GameTier: {}", item, tier);
                }}catch (Exception e){
                    BattleOfGods.LOGGER.error("Error while generating item tags for item {}", item, e);
                }
            }

        } catch (Exception e) {
            BattleOfGods.LOGGER.error("Error while generating item tags for {}", BattleOfGods.MODID, e);
        }
    }
}
