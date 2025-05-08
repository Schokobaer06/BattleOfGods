package com.schokobaer.battleofgods.tag;

import com.schokobaer.battleofgods.category.rarity.Rarity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class RarityTagProvider extends TagsProvider<Rarity> {

    public RarityTagProvider(PackOutput output, ResourceKey<? extends Registry<Rarity>> registryKey,
                             CompletableFuture<HolderLookup.Provider> lookupProvider, String modid,
                             @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registryKey, lookupProvider, modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        /*
        BattleOfGods.LOGGER.info("Gathering rarity data for {}", BattleOfGods.MODID);
        try {
            for (RegistryObject<Item> item : InitItem.ITEMS.getEntries()) {
                for (RegistryObject<Rarity> rarity : InitRarity.RARITIES.getEntries()) {
                    if (((ItemOverride) item.get()).getSubClassMethods().getRarity().equals(rarity.get())) {
                        tag(rarity.get().getTag()).addOptional(TagEntry.element(item.getId()).getId());
                        BattleOfGods.LOGGER.debug("Adding item {} to tag {} for {}", item.getId(), rarity.get().getTag().toString(), BattleOfGods.MODID);
                    }
                }
            }
        } catch (Exception e) {
            BattleOfGods.LOGGER.error("Error while gathering data for {}", BattleOfGods.MODID, e);
        }

         */
    }
}
