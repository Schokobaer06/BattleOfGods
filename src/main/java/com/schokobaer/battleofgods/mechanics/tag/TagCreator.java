package com.schokobaer.battleofgods.mechanics.tag;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitSubClass;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.mechanics.item.MainClass;
import com.schokobaer.battleofgods.mechanics.item.override.ItemOverride;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import com.schokobaer.battleofgods.mechanics.tier.Tier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
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
        TagKey<ItemOverride> tag = TagKey.create(InitSubClass.ITEM_OVERRIDE, new ResourceLocation(BattleofgodsMod.MODID, mainClass.getId().getPath().toLowerCase() + "/" + name.toLowerCase()));
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
/*
    public static TagKey<?> getTag(String name) {
        return tags.get(name.toLowerCase());
    }

 */
}
