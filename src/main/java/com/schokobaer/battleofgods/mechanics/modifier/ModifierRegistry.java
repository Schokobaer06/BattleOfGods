package com.schokobaer.battleofgods.mechanics.modifier;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModifierRegistry {
    // Speichert Basis-Modifier der Items
    private static final Map<Item, Map<Attribute, Double>> ITEM_BASE_MODIFIERS = new HashMap<>();

    // Speichert aktive dynamische Modifier (z.B. von anderen Items)
    private static final Map<UUID, Map<Attribute, Double>> DYNAMIC_MODIFIERS = new HashMap<>();

    // Registriert Basis-Modifier für ein Item
    public static void registerBaseModifier(Item item, Attribute attribute, double value) {
        ITEM_BASE_MODIFIERS.computeIfAbsent(item, k -> new HashMap<>()).put(attribute, value);
    }

    // Fügt dynamischen Modifier hinzu
    public static void addDynamicModifier(UUID source, Attribute attribute, double value) {
        DYNAMIC_MODIFIERS.computeIfAbsent(source, k -> new HashMap<>()).put(attribute, value);
    }

    // Entfernt dynamischen Modifier
    public static void removeDynamicModifier(UUID source, Attribute attribute) {
        if (DYNAMIC_MODIFIERS.containsKey(source)) {
            DYNAMIC_MODIFIERS.get(source).remove(attribute);
        }
    }

    public static double getBaseModifierValue(Item item, Attribute attribute) {
        return ITEM_BASE_MODIFIERS.getOrDefault(item, Collections.emptyMap())
                .getOrDefault(attribute, 0.0);
    }

    public static double getTotalValue(Item item, Attribute attribute) {
        double total = getBaseModifierValue(item, attribute);

        for (Map<Attribute, Double> modifiers : DYNAMIC_MODIFIERS.values()) {
            total += modifiers.getOrDefault(attribute, 0.0);
        }

        return Mth.clamp(total, 0.0, 100.0);
    }
}