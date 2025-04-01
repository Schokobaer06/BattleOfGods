package com.schokobaer.battleofgods.client.widget;

import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MaterialWidget extends AbstractWidget {
    private final RecipeHandler.BattleRecipe.IngredientEntry entry;
    private final Player player;
    private static final int ITEM_SIZE = 16;

    public MaterialWidget(int x, int y, RecipeHandler.BattleRecipe.IngredientEntry entry, Player player) {
        super(x, y, 100, 20, Component.empty());
        this.entry = entry;
        this.player = player;
    }



    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        assert Minecraft.getInstance().level != null;
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();

        // 1. Rendere Item-Icon
        ItemStack iconStack = entry.ingredient().getItems()[0];
        guiGraphics.renderItem(iconStack, getX(), getY());

        // 2. Rendere Text (Anzahl)
        int available = getAvailableCount();
        int required = entry.count();
        ChatFormatting color = (available >= required) ? ChatFormatting.WHITE : ChatFormatting.RED;

        guiGraphics.drawString(
                Minecraft.getInstance().font,
                Component.literal(available + "/" + required).withStyle(color),
                getX() + ITEM_SIZE + 5, getY() + 5,
                0xFFFFFF,
                false
        );
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }
    private int getAvailableCount() {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (entry.ingredient().test(stack)) {
                count += stack.getCount();
                if (count >= entry.count()) break;
            }
        }
        return count;
    }
}
