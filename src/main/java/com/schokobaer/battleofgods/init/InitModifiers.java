package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.modifier.ModifierRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitModifiers {
    public static final DeferredRegister<Attribute> MODIFIERS =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, BattleofgodsMod.MODID);

    public static final RegistryObject<Attribute> MODIFIER_CRITICAL_HIT_CHANCE = MODIFIERS.register(
            "critical_hit_chance",
            () -> new RangedAttribute(
                    "attribute.battleofgods.critical_hit_chance",
                    0.0D,
                    0.0D,
                    100.0D
            ).setSyncable(true)
    );

    public static void registerModifiers() {
        // Beispiel-Registrierung für dein Schwert
        ModifierRegistry.registerBaseModifier(
                Items.DIAMOND_SWORD, // Ersetze mit deinem Item
                MODIFIER_CRITICAL_HIT_CHANCE.get(),
                16.0
        );
    }
}
