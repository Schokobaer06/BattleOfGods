package com.schokobaer.battleofgods.network;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.handler.RecipeHandler;
import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CraftPacket {
    private final ResourceLocation recipeId;

    public CraftPacket(ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }

    public CraftPacket(FriendlyByteBuf buf) {
        this.recipeId = buf.readResourceLocation();
    }

    public static CraftPacket decode(FriendlyByteBuf buffer) {
        return new CraftPacket(buffer.readResourceLocation());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(recipeId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.containerMenu instanceof WorkbenchMenu menu) {
                RecipeHandler.getRecipeById(recipeId).ifPresent(recipe -> {
                    if (recipe.getGroup().equals(menu.getRecipeGroup())) {
                        if (BattleOfGods.isDebug())
                            BattleOfGods.LOGGER.debug("CraftPacket - handle has been called: Crafting item: {}", recipeId);
                        menu.craftItem(player, recipeId);
                    } else {
                        BattleOfGods.LOGGER.error("Recipe group mismatch: {} != {}", recipe.getGroup(), menu.getRecipeGroup());
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
