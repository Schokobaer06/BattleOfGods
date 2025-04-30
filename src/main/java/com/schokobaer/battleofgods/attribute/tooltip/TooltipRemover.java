package com.schokobaer.battleofgods.attribute.tooltip;

import com.schokobaer.battleofgods.BattleofgodsMod;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID)
@OnlyIn(Dist.CLIENT)
public class TooltipRemover {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        event.getToolTip().removeIf(component -> component.getString().contains(
                Component.translatable("attribute.battleofgods.generic.critical_hit").getString()
        ));
        event.getToolTip().removeIf(component -> component.getString().contains(
                Component.translatable("attribute.name.generic.attack_knockback").getString()
        ));
    }
}
