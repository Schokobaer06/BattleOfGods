package com.schokobaer.battleofgods.tag;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.subClass.MainClass;
import com.schokobaer.battleofgods.override.ItemOverride;
import com.schokobaer.battleofgods.rarity.Rarity;
import com.schokobaer.battleofgods.tier.Tier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;



/**
 * Creates tags for every main class, subclass, tier, rarity
 */

public class TagCreator {
    //private static final Map<String, TagKey<?>> tags = new HashMap<>();

    public static TagKey<MainClass> createMainClassTag(@NotBlank String name) {
        TagKey<MainClass> tag = TagKey.create(InitMainClass.MAIN_CLASSES.getRegistryKey(), new ResourceLocation(BattleofgodsMod.MODID, name.toLowerCase()));
        //tags.put(name.toLowerCase(), tag);
        return tag;
    }

    public static TagKey<ItemOverride> createSubClassTag(@NotBlank String name, RegistryObject<MainClass> mainClass) {
        TagKey<ItemOverride> tag = TagKey.create((net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<ItemOverride>>) (Object) ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(BattleofgodsMod.MODID, name.toLowerCase()));
        //tags.put(name.toLowerCase(), tag);
        return tag;
    }

    public static TagKey<Tier> createTierTag(String name) {
        TagKey<Tier> tag = TagKey.create(InitTier.TIERS.getRegistryKey(), new ResourceLocation(BattleofgodsMod.MODID, name.toLowerCase()));
        //tags.put(name.toLowerCase(), tag);
        return tag;
    }

    public static TagKey<Rarity> createRarityTag(String name) {
        TagKey<Rarity> tag = TagKey.create(InitRarity.RARITIES.getRegistryKey(), new ResourceLocation(BattleofgodsMod.MODID, name.toLowerCase()));
        //tags.put(name.toLowerCase(), tag);
        return tag;
    }
}
