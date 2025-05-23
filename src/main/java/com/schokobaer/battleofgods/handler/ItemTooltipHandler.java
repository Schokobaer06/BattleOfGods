package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemTooltipHandler {
    private static final boolean isGlobal = true;

    public static boolean isGlobal() {
        return isGlobal;
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        String modId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getNamespace();
        Style style = AbstractSubClass.getStyle();

        // Nur fremde Items bearbeiten, wenn global aktiv
        if (!isGlobal) return;

        List<Component> tooltip = event.getToolTip();
        int insertPos = Math.min(1, tooltip.size()); // Position nach dem Item-Namen

        // Can be placed (Blöcke, Möbel etc.)
        if (isPlaceable(item)) {
            tooltip.add(insertPos, Component.translatable("tooltip.battleofgods.placeable").withStyle(style));
        }

        // Consumable (verbrauchbare Items)
        if (isConsumable(stack)) {
            tooltip.add(insertPos, Component.translatable("tooltip.battleofgods.consumable").withStyle(style));
        }

        // Material (Handwerksmaterial)
        if (RecipeHandler.isMaterial(item)) {
            if (tooltip.size() >= 5)
                tooltip.add(5, Component.translatable("tooltip.battleofgods.material").withStyle(style));
            else
                tooltip.add(Component.translatable("tooltip.battleofgods.material").withStyle(style));
        }

        // Equipable (Rüstung, Accessoires etc.)
        if (isEquipable(item)) {
            tooltip.add(insertPos, Component.translatable("tooltip.battleofgods.equipable").withStyle(style));
        }

        // Ammo (Munition)
        if (isAmmo(item)) {
            tooltip.add(insertPos, Component.translatable("tooltip.battleofgods.ammo").withStyle(style));
        }

        // Rarity
        if (!(modId.equals(BattleOfGods.MODID))){
            Rarity rarity = item.getRarity(stack);
            tooltip.add(1, Component.literal(rarity.name()).withStyle(rarity.getStyleModifier()));
        }
    }

    // Hilfsmethoden für die Kategorien
    public static boolean isPlaceable(Item item) {
        return item instanceof BlockItem ||
                item instanceof PlaceOnWaterBlockItem ||
                item instanceof StandingAndWallBlockItem;
    }

    public static boolean isConsumable(ItemStack stack) {
        return stack.getItem().isEdible() ||
                stack.getItem().getUseDuration(stack) > 0;
    }

    public static boolean isEquipable(Item item) {
        return item instanceof Equipable || // Für Forge's Equipable-Interface
                item instanceof ElytraItem;// Für Vanilla Wearable-Items
    }

    public static boolean isAmmo(Item item) {
        // Beispiel: Pfeile, Feuerkugeln etc.
        return item instanceof ArrowItem ||
                item.getDefaultInstance().is(ItemTags.ARROWS) ||
                item instanceof ProjectileWeaponItem; // Falls ein Ammo-Tag existiert
    }
}