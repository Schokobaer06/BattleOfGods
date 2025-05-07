package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.category.subClass.TerrariaArmorItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.schokobaer.battleofgods.BattleofgodsMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class DefenseHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            // Berechne gesamte Defense

            int totalDefense = 0;
            for (ItemStack stack : player.getArmorSlots()) {
                if (stack.getItem() instanceof TerrariaArmorItem armor)
                    totalDefense += armor.getDefenseValue();
            }
            // Terraria Defense-Formel: Reduziere Schaden um Defense * 0.5
            float reducedDamage = event.getAmount() - (totalDefense * 0.5f);
            event.setAmount(Math.max(reducedDamage, 1));
        }
    }
}