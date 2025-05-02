package com.schokobaer.battleofgods.armor;

import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.classes.MainClass;
import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.rarity.Rarity;
import com.schokobaer.battleofgods.tier.Tier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TerrariaArmorItem extends ArmorItem {
    private final int defense;
    private final Map<Attribute, AttributeModifier> attributeModifiers = new HashMap<>();
    private final RegistryObject<Rarity> rarity;
    private final RegistryObject<Tier> tier;
    private final RegistryObject<MainClass> mainclass = InitMainClass.ARMOR;

    public TerrariaArmorItem(ArmorMaterial material, Type type, Properties properties, int defense, RegistryObject<Rarity> rarity, RegistryObject<Tier> tier) {
        super(material, type, properties);
        this.defense = defense;
        this.rarity = rarity;
        this.tier = tier;
    }

    public Rarity getRarity() {
        return this.rarity.get();
    }

    public Tier getTier() {
        return this.tier.get();
    }

    public MainClass getMainClass() {
        return this.mainclass.get();
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

    @Override
    public Component getName(ItemStack stack) {
        Component name = super.getName(stack);
        return name.copy().withStyle(Style.EMPTY.withColor(this.getRarity().getColor()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        //Rarity
        tooltip.add(Component.translatable("rarity.battleofgods." +
                this.getRarity().getDisplayName().toLowerCase()).setStyle(
                Style.EMPTY.withBold(true)
                        .withColor(this.getRarity().getColor())
                        .withItalic(true)
        ));
        //Defense
        if (tooltip.size() > 1)
            tooltip.add(1, Component.literal(this.getDefense() + " ")
                    .append(Component.translatable("tooltip.battleofgods.armor"))
                    .withStyle(ChatFormatting.DARK_GREEN));
    }

    @Override
    public boolean isRepairable(@NotNull ItemStack itemstack) {
        return false;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(itemstack.getItem());
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
