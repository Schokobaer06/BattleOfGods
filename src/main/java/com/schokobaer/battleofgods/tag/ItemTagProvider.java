package com.schokobaer.battleofgods.tag;

import com.schokobaer.battleofgods.BattleOfGods;
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


    public ItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        try {
            for (RegistryObject<Item> item : InitItem.ITEMS.getEntries()) {
                BattleOfGods.LOGGER.debug(item.get().getClass().getSuperclass().getSuperclass().toString());
            }

        } catch (Exception e) {
            BattleOfGods.LOGGER.error("Error while generating item tags for {}", BattleOfGods.MODID, e);
        }
    }
}
