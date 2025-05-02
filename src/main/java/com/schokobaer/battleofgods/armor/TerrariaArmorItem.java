package com.schokobaer.battleofgods.armor;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TerrariaArmorItem extends ArmorItem {
    private final int defense;
    private final Map<Attribute, AttributeModifier> attributeModifiers = new HashMap<>();

    public TerrariaArmorItem(ArmorMaterial material, Type type, Properties properties, int defense) {
        super(material, type, properties);
        this.defense = defense;
    }

    // Getter für Defense (Terraria-Style)
    public int getDefenseValue() {
        return defense;
    }

    // Deaktiviere Minecrafts Standard-Rüstungsberechnung
    @Override
    public int getDefense() {
        return 0;
    }

    // Füge individuelle Attribute hinzu
    public TerrariaArmorItem addAttributeModifier(Attribute attribute, double amount, AttributeModifier.Operation operation) {
        UUID uuid = UUID.randomUUID();
        attributeModifiers.put(attribute, new AttributeModifier(uuid, "Armor Modifier", amount, operation));
        return this;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        if (slot == this.type.getSlot()) {
            attributeModifiers.forEach((attr, mod) -> {
                AttributeModifier slotSpecificModifier = new AttributeModifier(
                        mod.getId(),
                        mod.getName(),
                        mod.getAmount(),
                        mod.getOperation()
                );
                modifiers.put(attr, slotSpecificModifier);
            });
        }
        return modifiers;
    }
}
