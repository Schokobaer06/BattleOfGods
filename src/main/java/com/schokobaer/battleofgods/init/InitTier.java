package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.category.tier.Tier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitTier {
    //TODO
    public static final ResourceKey<Registry<Tier>> TIER_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(BattleofgodsMod.MODID, "tiers"));
    public static final DeferredRegister<Tier> TIERS = DeferredRegister.create(TIER_KEY, BattleofgodsMod.MODID);

    public static final RegistryObject<Tier> TIER_1 = TIERS.register("tier_1", () -> new Tier("Tier 1"));

}