package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.Tier;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

public class InitTier {
    public static final ResourceKey<Registry<Tier>> TIER_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(BattleofgodsMod.MODID, "tiers"));
    public static final DeferredRegister<Tier> TIERS = DeferredRegister.create(TIER_KEY, BattleofgodsMod.MODID);

    public static final RegistryObject<Tier> TIER_1 = TIERS.register("tier_1", () -> new Tier("Tier 1", createTag("tier_1")));
    public static final RegistryObject<Tier> TIER_2 = TIERS.register("tier_2", () -> new Tier("Tier 2", createTag("tier_2")));

    private static TagKey<Item> createTag(String name) {
        return ItemTags.create(new ResourceLocation(BattleofgodsMod.MODID, name));
    }
}