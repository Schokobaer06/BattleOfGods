package com.schokobaer.battleofgods.tag;

import com.schokobaer.battleofgods.category.tier.Tier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class TierTagProvider extends TagsProvider<Tier> {

    public TierTagProvider(PackOutput output, ResourceKey<? extends Registry<Tier>> registryKey,
                           CompletableFuture<HolderLookup.Provider> lookupProvider, String modid,
                           @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registryKey, lookupProvider, modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        /*
        BattleOfGods.LOGGER.info("Gathering tier data for {}", BattleOfGods.MODID);
        try {
            for (RegistryObject<Item> item : InitItem.ITEMS.getEntries()) {
                for (RegistryObject<Tier> tier : InitTier.TIERS.getEntries()) {
                    if (((ItemOverride) item.get()).getSubClassMethods().getTier().equals(tier.get())) {
                        tag(tier.get().getTag()).addOptional(TagEntry.element(item.getId()).getId());
                        BattleOfGods.LOGGER.debug("Adding item {} to tag {} for {}", item.getId(), tier.get().getTag().toString(), BattleOfGods.MODID);
                    }
                }
            }
        } catch (Exception e) {
            BattleOfGods.LOGGER.error("Error while gathering data for {}", BattleOfGods.MODID, e);
        }

         */
    }
}