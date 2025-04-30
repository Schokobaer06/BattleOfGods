package com.schokobaer.battleofgods.network.packet;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.client.gui.WorkbenchScreen;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectedRecipePacket {
    private final ResourceLocation recipeId;

    // Konstruktor für Client → Server
    public SelectedRecipePacket(ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }

    // Serialisierung
    public static void encode(SelectedRecipePacket msg, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(msg.recipeId);
    }

    // Deserialisierung
    public static SelectedRecipePacket decode(FriendlyByteBuf buffer) {
        return new SelectedRecipePacket(buffer.readResourceLocation());
    }

    // Verarbeitung auf dem Server
    public static void handle(SelectedRecipePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.containerMenu instanceof WorkbenchMenu menu) {
                RecipeHandler.getRecipeById(msg.recipeId).ifPresent(menu::setSelectedRecipe);
                if (BattleofgodsMod.isDebug())
                    BattleofgodsMod.LOGGER.debug("SelectedRecipePacket - handle has been called: Selected recipe: {}", msg.recipeId);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
