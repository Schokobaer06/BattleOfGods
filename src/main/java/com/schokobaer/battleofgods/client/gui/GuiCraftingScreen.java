package com.schokobaer.battleofgods.client.gui;

import com.schokobaer.battleofgods.world.inventory.GuiCraftingMenu;
import com.schokobaer.battleofgods.CustomRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.ArrayList;

public class GuiCraftingScreen extends AbstractContainerScreen<GuiCraftingMenu> {
    private final Minecraft minecraft;
    private List<Button> recipeButtons = new ArrayList<>();
    private final List<CustomRecipe> availableRecipes = new ArrayList<>();

    public GuiCraftingScreen(GuiCraftingMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    protected void init() {
        super.init();
        this.recipeButtons.clear();
        addRecipeButtons();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    private void addRecipeButtons() {
        int xOffset = this.width / 2 - 100;
        int yOffset = this.height / 2 - 50;

        for (int i = 0; i < availableRecipes.size(); i++) {
            CustomRecipe recipe = availableRecipes.get(i);
            Component textComponent = recipe.getOutput().getDisplayName();

            Button recipeButton = Button.builder(textComponent, button -> {
                // Handle button click (craft item)
            }).pos(xOffset, yOffset + (i * 25)).size(200, 20).build();

            addRenderableWidget(recipeButton);
            recipeButtons.add(recipeButton);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
    }

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
	    // Hier kannst du dein eigenes Rendering für den Hintergrund implementieren
	    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	    graphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF000000); // Schwarzer Hintergrund
	    //graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}



    public void setAvailableRecipes(List<CustomRecipe> recipes) {
        this.availableRecipes.clear();
        this.availableRecipes.addAll(recipes);
    }
}
