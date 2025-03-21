package com.schokobaer.battleofgods.mechanics.tag;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitSubClass;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.mechanics.item.MainClass;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import com.schokobaer.battleofgods.mechanics.tier.Tier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import static com.schokobaer.battleofgods.init.InitTier.TIER_KEY;

/**
 * Creates tags for every main class, subclass, tier, rarity
 */
public class TagCreator {
    public static TagKey<MainClass> createMainClassTag(@NotBlank String name){
        return TagKey.create(InitMainClass.MAIN_CLASSES.getRegistryKey(),new ResourceLocation(BattleofgodsMod.MODID,name.toLowerCase()));
    }
    public static TagKey<Item> createSubClassTag(@NotBlank String name, RegistryObject<MainClass> mainClass){
        return TagKey.create(InitSubClass.SUBCLASSES.getRegistryKey(),new ResourceLocation(BattleofgodsMod.MODID, mainClass.getId().getPath().toLowerCase() + "/"+name.toLowerCase()));
    }
    public static TagKey<Tier> createTierTag(String name) {
        return TagKey.create(InitTier.TIERS.getRegistryKey(), new ResourceLocation(BattleofgodsMod.MODID, name));
    }
    public static TagKey<Rarity> createRarityTag(String name) {
        return TagKey.create(InitRarity.RARITIES.getRegistryKey(), new ResourceLocation(BattleofgodsMod.MODID, name));
    }
}
