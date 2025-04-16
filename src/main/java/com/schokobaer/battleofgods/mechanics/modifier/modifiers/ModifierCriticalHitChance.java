package com.schokobaer.battleofgods.mechanics.modifier.modifiers;

import com.schokobaer.battleofgods.init.InitModifiers;
import com.schokobaer.battleofgods.mechanics.modifier.Modifier;
import com.schokobaer.battleofgods.mechanics.modifier.ModifierRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class ModifierCriticalHitChance extends Modifier {
    private static final UUID CRITICAL_UUID = UUID.fromString("d6a46c0e-7b0a-4e7a-8f2a-5c1b8f3e9d2c");
    private static final ModifierCriticalHitChance INSTANCE = new ModifierCriticalHitChance();

    private ModifierCriticalHitChance() {
        super(
                InitModifiers.MODIFIER_CRITICAL_HIT_CHANCE.get(),
                CRITICAL_UUID,
                "Weapon Critical Bonus"
        );
    }

    public static ModifierCriticalHitChance get() {
        return INSTANCE;
    }

    @Override
    public AttributeModifier createModifier(double value) {
        return new AttributeModifier(
                getUUID(),
                getName(),
                value,
                AttributeModifier.Operation.ADDITION
        );
    }

    @SubscribeEvent
    public void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player && event.getSlot() == EquipmentSlot.MAINHAND) {
            updateModifier(player);
        }
    }

    private void updateModifier(Player player) {
        AttributeInstance instance = player.getAttribute(getAttribute());
        ItemStack stack = player.getMainHandItem();

        if (instance != null) {
            instance.removeModifier(getUUID());

            if (!stack.isEmpty()) {
                // Korrektur: getTotalValue statt getModifierValue
                double value = ModifierRegistry.getTotalValue(stack.getItem(), getAttribute());
                if (value > 0) {
                    instance.addTransientModifier(createModifier(value));
                }
            }
        }
    }

    // Initialisierung im Static-Block
    static {
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    @SubscribeEvent
    public void onItemEquip(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            handleEquipmentChange(player, event.getFrom(), event.getTo());
        }
    }

    private void handleEquipmentChange(Player player, ItemStack oldStack, ItemStack newStack) {
        // Entferne alte Modifier
        if (!oldStack.isEmpty()) {
            UUID itemUUID = getItemUUID(oldStack);
            ModifierRegistry.removeDynamicModifier(itemUUID, getAttribute());
        }

        // Füge neue Modifier hinzu
        if (!newStack.isEmpty()) {
            UUID itemUUID = getItemUUID(newStack);
            double value = ModifierRegistry.getTotalValue(newStack.getItem(), getAttribute());
            ModifierRegistry.addDynamicModifier(itemUUID, getAttribute(), value);
        }

        updateModifier(player);
    }

    private UUID getItemUUID(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains("modifier_uuid")) {
            UUID uuid = UUID.randomUUID();
            stack.getOrCreateTag().putUUID("modifier_uuid", uuid);
            return uuid;
        }
        return stack.getTag().getUUID("modifier_uuid");
    }
}