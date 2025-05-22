package com.schokobaer.battleofgods.handler;


import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.SubClassMethods;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemTooltipHandler {
    private static final boolean isGlobal = true;
    /*
    /// EVERY ITEM
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        Item item = event.getItemStack().getItem();
        List<Component> tooltip = event.getToolTip();
        if (isGlobal){

            /// Material
            if (RecipeHandler.isMaterial(item))
                tooltip.add(1, Component.translatable("tooltip.battleofgods.material"));
        }
        else {
            if (item.getClass().getSuperclass() != null && SubClassMethods.class.isAssignableFrom(item.getClass().getSuperclass())) {
                SubClassMethods subClassItem = (SubClassMethods) item.getClass().getSuperclass().cast(item);

                /// Material
                if (RecipeHandler.isMaterial((Item) subClassItem))
                    tooltip.add(1, Component.translatable("tooltip.battleofgods.material"));
            }
        }


    }

    /**
     * Should tooltips apply to every item or only to item from this mod
     * @return true if tooltips should apply to every item
     */
    public static boolean isGlobal() {
        return isGlobal;
    }
}
