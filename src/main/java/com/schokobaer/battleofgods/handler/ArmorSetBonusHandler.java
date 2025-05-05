package com.schokobaer.battleofgods.handler;


import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.armor.ArmorSetManager;
import com.schokobaer.battleofgods.armor.tier1.WoodArmor;
import com.schokobaer.battleofgods.init.InitItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.UUID;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID)
public class ArmorSetBonusHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == PlayerTickEvent.Phase.END) {
            ArmorSetManager.applySetBonuses(event.player);
        }
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        //BattleofgodsMod.LOGGER.debug("onCommonSetup wurde aufgerufen");
        // Register armor sets and bonuses here
        // Example: ArmorSetManager.registerArmorSet("example_set", InitArmor.EXAMPLE_ARMOR.get());
        // ArmorSetManager.registerSetBonus("example_set_id", new ArmorSetManager.AttributeModifierBonus(
        //  "example_attribute", new AttributeModifier(...)
        // ));
        ArmorSetManager.registerArmorSet(WoodArmor.name, InitItem.WOOD_ARMOR_HELMET.get(),
                InitItem.WOOD_ARMOR_CHESTPLATE.get(),
                InitItem.WOOD_ARMOR_LEGGINGS.get(),
                InitItem.WOOD_ARMOR_BOOTS.get());
        ArmorSetManager.registerSetBonus(WoodArmor.name, new ArmorSetManager.AttributeModifierBonus(
                Attributes.ARMOR,
                new AttributeModifier(
                        UUID.fromString("d4f3b8a0-5c1e-11ec-bf63-0242ac130002"),
                        WoodArmor.name + "_set_bonus",
                        1d,
                        AttributeModifier.Operation.ADDITION
                )
        ));
        if (BattleofgodsMod.isDebug()) {
            BattleofgodsMod.LOGGER.debug("Registered armor sets: {}", ArmorSetManager.getAllArmorSets());
            BattleofgodsMod.LOGGER.debug("Registered set bonuses: {}", ArmorSetManager.getAllSetBonuses());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        Consumer<Player> setBonus = ArmorSetManager.getSetBonus(event.getItemStack().getItem());


        if (setBonus instanceof ArmorSetManager.AttributeModifierBonus bonus) {
            event.getToolTip().add(
                    Component.translatable("tooltip.battleofgods.armor_set_bonus")
                            .withStyle(ChatFormatting.BLUE)
                            .append(": ")
                            .append(String.valueOf(bonus)
                            ));
            if (BattleofgodsMod.isDebug()) {
                BattleofgodsMod.LOGGER.debug("Item: {}, Set Bonus: {}", event.getItemStack().getItem(), setBonus.toString());
            }
        }

    }

}
