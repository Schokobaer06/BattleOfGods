package com.schokobaer.battleofgods.tag;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitItem;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.override.ItemOverride;
import com.schokobaer.battleofgods.tier.Tier;
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

public class TierTagProvider extends TagsProvider<Tier> {

    public TierTagProvider(PackOutput output, ResourceKey<? extends Registry<Tier>> registryKey,
                             CompletableFuture<HolderLookup.Provider> lookupProvider, String modid,
                             @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registryKey, lookupProvider, modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        BattleofgodsMod.LOGGER.info("Gathering tier data for {}", BattleofgodsMod.MODID);
        try {
            for (RegistryObject<Item> item : InitItem.ITEMS.getEntries()) {
                for (RegistryObject<Tier> tier : InitTier.TIERS.getEntries()) {
                    if (((ItemOverride) item.get()).getSubClassMethods().getTier().equals(tier.get())){
                        tag(tier.get().getTag()).addOptional(TagEntry.element(item.getId()).getId());
                        BattleofgodsMod.LOGGER.debug("Adding item {} to tag {} for {}", item.getId(), tier.get().getTag().toString(), BattleofgodsMod.MODID);
                    }
                }
            }
        } catch (Exception e) {
            BattleofgodsMod.LOGGER.error("Error while gathering data for {}", BattleofgodsMod.MODID, e);
        }
    }
}