package com.schokobaer.battleofgods.armor;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ArmorSetManager {
    private static final Map<String, Set<Item>> armorSets = new HashMap<>();
    private static final Map<String, Consumer<Player>> setBonuses = new HashMap<>();


    /**
     * Registers an armor set with the given ID and its pieces.
     *
     * @param setId  The ID of the armor set.
     *               for example "wood_armor"
     * @param pieces The pieces of the armor set.
     *               for example: InitItem.WOOD_ARMOR_HELMET.get(),INITItem.WOOD_ARMOR_CHESTPLATE.get(),...
     */
    public static void registerArmorSet(String setId, Item... pieces) {
        armorSets.put(setId, new HashSet<>(Arrays.asList(pieces)));
    }


    /**
     * Registers a set bonus for the given armor set ID.
     *
     * @param setId The ID of the armor set.
     *              for example "wood_armor"
     * @param bonus The bonus to apply when the full set is worn.
     *              for example: new AttributeModifierBonus(Attributes.ARMOR, new AttributeModifier(...))
     */
    public static void registerSetBonus(String setId, AttributeModifierBonus bonus) {
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
            //var attributeInstance = player.getAttribute(Attributes.ARMOR); // Beispielattribut
            Consumer<Player> bonus = setBonuses.get(setId);

            if (hasFullSet(player, setId)) {
                // Set-Bonus anwenden
                if (bonus instanceof AttributeModifierBonus modifierBonus) {
                    var attributeInstance = player.getAttribute(modifierBonus.attribute());
                    if (attributeInstance != null && attributeInstance.getModifier(modifierBonus.modifier().getId()) == null) {
                        modifierBonus.accept(player);
                    }
                } else {
                    bonus.accept(player);
                }
            } else {
                // Set-Bonus entfernen, wenn nicht mehr vollständig
                if (bonus instanceof AttributeModifierBonus modifierBonus) {
                    var attributeInstance = player.getAttribute(modifierBonus.attribute());
                    if (attributeInstance != null && attributeInstance.getModifier(modifierBonus.modifier().getId()) != null) {
                        attributeInstance.removeModifier(modifierBonus.modifier().getId());
                    }
                }
            }

        });
    }

    public static List<Item> getArmorSet(String setId) {
        return new ArrayList<>(armorSets.getOrDefault(setId, Collections.emptySet()));
    }

    public static Consumer<Player> getSetBonus(String setId) {
        return setBonuses.getOrDefault(setId, null);
    }

    public static Map<String, Consumer<Player>> getAllSetBonuses() {
        return setBonuses;
    }

    public static Map<String, Set<Item>> getAllArmorSets() {
        return armorSets;
    }

    public static Consumer<Player> getSetBonus(Item item) {
        for (Map.Entry<String, Set<Item>> entry : armorSets.entrySet()) {
            if (entry.getValue().contains(item)) {
                return setBonuses.get(entry.getKey());
            }
        }
        return null;
    }

    public record AttributeModifierBonus(Attribute attribute, AttributeModifier modifier) implements Consumer<Player> {

        @Override
        public void accept(Player player) {
            var attributeInstance = player.getAttribute(attribute);
            if (attributeInstance != null) {
                // Prüfen, ob der Modifier bereits existiert
                if (attributeInstance.getModifier(modifier.getId()) == null) {
                    attributeInstance.addTransientModifier(modifier);
                }
            }
        }

        @Override
        public @NotNull String toString() {
            String sign = (modifier.getAmount() >= 0) ? "+" : "-";
            double amount = modifier.getAmount();
            String formattedAmount = (amount == (int) amount) ? String.valueOf((int) amount) : String.valueOf(amount);
            return sign + formattedAmount + " " + net.minecraft.client.resources.language.I18n.get(attribute.getDescriptionId());
        }
    }
}