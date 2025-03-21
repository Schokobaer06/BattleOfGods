package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.tier.Tier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.schokobaer.battleofgods.mechanics.tag.TagCreator.createTierTag;

public class InitTier {
    public static final ResourceKey<Registry<Tier>> TIER_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(BattleofgodsMod.MODID, "tiers"));
    public static final DeferredRegister<Tier> TIERS = DeferredRegister.create(TIER_KEY, BattleofgodsMod.MODID);

    public static final RegistryObject<Tier> TIER_1 = TIERS.register("tier_1", () -> new Tier("Tier 1", createTierTag("tier_1")));
    //public static final RegistryObject<Tier> TIER_2 = TIERS.register("tier_2", () -> new Tier("Tier 2", createTag("tier_2")));

}