package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.armor.TerrariaArmorItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID)
@OnlyIn(Dist.CLIENT)
public class TooltipRemoveHandler {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof Item) {

            //Critical Hit Chance
            event.getToolTip().removeIf(component -> component.contains(
                    Component.translatable("attribute.battleofgods.generic.critical_hit")
            ));
            //Knockback Chance
            event.getToolTip().removeIf(component -> component.contains(
                    Component.translatable("attribute.name.generic.attack_knockback")
            ));
            // Damage
            event.getToolTip().removeIf(component -> component.contains(
                    Component.translatable("attribute.name.generic.attack_damage")
            ));
            // Attack Speed
            event.getToolTip().removeIf(component -> component.contains(
                    Component.translatable("attribute.name.generic.attack_speed")
            ));

        }
        if (event.getItemStack().getItem() instanceof TerrariaArmorItem)
            //Armor
            event.getToolTip().removeIf(component -> component.contains(
                    Component.translatable("attribute.name.generic.armor")
            ));
    }
}
