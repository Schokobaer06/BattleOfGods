package com.schokobaer.battleofgods.client.widget;

import com.schokobaer.battleofgods.handler.RecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RecipeButton extends AbstractWidget {
    private static final ResourceLocation BUTTON_TEXTURE =
            new ResourceLocation("battleofgods:textures/gui/recipe_button.png");

    private final RecipeHandler.BattleRecipe recipe;
    public Boolean isFocused = false;
    public Boolean isHovered = false;

    public RecipeButton(int x, int y, RecipeHandler.BattleRecipe recipe) {
        super(x, y, 18, 18, Component.empty());
        this.recipe = recipe;
    }


    @Override
    protected void renderWidget(GuiGraphics graphics, int x, int y, float partialTicks) {
        assert Minecraft.getInstance().level != null;
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        ItemStack resultItem = recipe.getResultItem(registryAccess);

        int xOffset = isHoveredOrFocused() ? 18 : 0;

        // 1. Render button background
        graphics.blit(BUTTON_TEXTURE,
                getX(), getY(),
                xOffset, 0,
                this.width, this.height,
                36, 18);

        // 2. Render item
        graphics.renderItem(resultItem, getX() + 1, getY() + 1);
        graphics.renderItemDecorations(
                Minecraft.getInstance().font,
                resultItem,
                getX() + 1,
                getY() + 1
        );
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }

    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        assert Minecraft.getInstance().level != null;
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        guiGraphics.renderTooltip(
                Minecraft.getInstance().font,
                recipe.getResultItem(registryAccess),
                mouseX, mouseY
        );
    }

    public RecipeHandler.BattleRecipe getRecipe() {
        return recipe;
    }


    @Override
    public boolean isHoveredOrFocused() {
        return isHovered || isFocused;
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

    @Override
    public boolean isHovered() {
        return isHovered;
    }
}
