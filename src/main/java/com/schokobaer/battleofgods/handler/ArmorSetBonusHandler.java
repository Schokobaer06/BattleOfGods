package com.schokobaer.battleofgods.handler;


import com.schokobaer.battleofgods.armor.ArmorSetManager;
import com.schokobaer.battleofgods.armor.tier1.WoodArmor;
import com.schokobaer.battleofgods.init.InitItem;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber
public class ArmorSetBonusHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == PlayerTickEvent.Phase.END) {
            ArmorSetManager.applySetBonuses(event.player);
        }
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        // Register armor sets and bonuses here
        // Example: ArmorSetManager.registerArmorSet("example_set", InitArmor.EXAMPLE_ARMOR.get());
        // ArmorSetManager.registerSetBonus("example_set", player -> { /* Apply bonus */ });
        ArmorSetManager.registerArmorSet(WoodArmor.name, InitItem.WOOD_ARMOR_HELMET.get(),
                InitItem.WOOD_ARMOR_CHESTPLATE.get(),
                InitItem.WOOD_ARMOR_LEGGINGS.get(),
                InitItem.WOOD_ARMOR_BOOTS.get());
        ArmorSetManager.registerSetBonus(WoodArmor.name, player -> {
            Objects.requireNonNull(player.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(
                    UUID.fromString("d4f3b8a0-5c1e-11ec-bf63-0242ac130002"),
                    WoodArmor.name + "_set_bonus",
                    1.0,
                    AttributeModifier.Operation.ADDITION
            ));
        });
    }

}
