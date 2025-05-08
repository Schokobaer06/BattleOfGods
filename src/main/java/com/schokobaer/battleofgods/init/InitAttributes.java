package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, BattleOfGods.MODID);

    public static final RegistryObject<Attribute> CRITICAL_HIT_CHANCE = ATTRIBUTES.register(
            "critical_hit_chance",
            () -> new RangedAttribute(
                    "attribute.battleofgods.generic.critical_hit",
                    0.04,  // Standardwert (4%)
                    0.0,   // Min-Wert
                    1.0    // Max-Wert (100%)
            ).setSyncable(true)
    );
}
