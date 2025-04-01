package com.schokobaer.battleofgods.client.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class RecipeButton extends AbstractWidget {
    private static final ResourceLocation BUTTON_TEXTURE =
            new ResourceLocation("battleofgods:textures/gui/recipe_button.png");

    private final RecipeHandler.BattleRecipe recipe;
    private final Consumer<RecipeHandler.BattleRecipe> onSelect;

    public RecipeButton(int x, int y, RecipeHandler.BattleRecipe recipe, Consumer<RecipeHandler.BattleRecipe> onSelect) {
        super(x, y, 16, 16,Component.empty());
        this.recipe = recipe;
        this.onSelect = onSelect;
    }


    @Override
    protected void renderWidget(GuiGraphics graphics, int x, int y, float partialTicks) {
        assert Minecraft.getInstance().level != null;
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        ItemStack resultItem = recipe.getResultItem(registryAccess);

        // 1. Render button background
        graphics.blit(BUTTON_TEXTURE, getX(), getY(), 0, 0, 16, 16, 16, 16);

        // 2. Render item
        graphics.renderItem(resultItem, getX() + 2, getY() + 2);
        graphics.renderItemDecorations(
                Minecraft.getInstance().font,
                resultItem,
                getX() + 2,
                getY() + 2
        );
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }

    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        guiGraphics.renderTooltip(
                Minecraft.getInstance().font,
                recipe.getResultItem(registryAccess),
                mouseX, mouseY
        );
    }
    public void onSelect(double mouseX, double mouseY) {
        onSelect.accept(recipe);
        playDownSound(Minecraft.getInstance().getSoundManager());
    }
}
