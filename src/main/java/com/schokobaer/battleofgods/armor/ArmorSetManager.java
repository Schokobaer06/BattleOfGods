package com.schokobaer.battleofgods.armor;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ArmorSetManager {
    private static final Map<String, Set<Item>> armorSets = new HashMap<>();
    private static final Map<String, Consumer<Player>> setBonuses = new HashMap<>();

    public static void registerArmorSet(String setId, Item... pieces) {
        armorSets.put(setId, new HashSet<>(Arrays.asList(pieces)));
    }

    public static void registerSetBonus(String setId, Consumer<Player> bonus) {
        setBonuses.put(setId, bonus);
    }

    public static boolean hasFullSet(Player player, String setId) {
        Set<Item> required = armorSets.get(setId);
        Set<Item> equipped = Arrays.stream(player.getInventory().armor.toArray(new ItemStack[0]))
                .filter(stack -> !stack.isEmpty())
                .map(ItemStack::getItem)
                .collect(Collectors.toSet());

        return equipped.containsAll(required);
    }

    public static void applySetBonuses(Player player) {
        armorSets.keySet().forEach(setId -> {
            if (hasFullSet(player, setId)) {
                setBonuses.get(setId).accept(player);
            }
        });
    }
}
