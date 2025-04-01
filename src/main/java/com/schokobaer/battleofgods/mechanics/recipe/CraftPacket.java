package com.schokobaer.battleofgods.mechanics.recipe;

import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CraftPacket {
    private ResourceLocation recipeId;

    public CraftPacket(ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }

    public CraftPacket(FriendlyByteBuf buf) {
        this.recipeId = buf.readResourceLocation();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(recipeId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        /*
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            RecipeHandler.BattleRecipe recipe = RecipeHandler.RECIPES.stream()
                    .filter(r -> r.getId().equals(recipeId))
                    .findFirst()
                    .orElse(null);

            if (recipe != null) {
                player.level().getRecipeManager().byKey(recipeId)
                        .ifPresent(r -> {
                            player.awardRecipes(java.util.Collections.singleton(r));
                            player.getInventory().add(r.getResultItem(player.level().registryAccess()));
                        });
            }
        });
        ctx.get().setPacketHandled(true);

         */
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.containerMenu instanceof WorkbenchMenu menu) {
                menu.craftItem(player, recipeId);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
