package com.schokobaer.battleofgods.client.gui;

import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ScrollPanel;

import java.util.List;

public class WorkbenchScreen extends AbstractContainerScreen<WorkbenchMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation("battleofgods:textures/gui/workbench.png");

    private ScrollPanel recipeList;
    private List<RecipeHandler.BattleRecipe> visibleRecipes;
    private RecipeHandler.BattleRecipe selectedRecipe;

    public WorkbenchScreen(WorkbenchMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();

        // Scrollbare Rezeptliste (Links)
        this.visibleRecipes = RecipeHandler.getCraftableRecipes(minecraft.player);
        this.recipeList = new ScrollPanel(
                this.minecraft,
                this.width / 4,
                this.height,
                20,
                this.height - 50,
                10,
                20
        ) {
            @Override
            protected int getContentHeight() {
                return visibleRecipes.size() * 30;
            }

            @Override
            protected void drawPanel(PoseStack stack, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
                for (int i = 0; i < visibleRecipes.size(); i++) {
                    RecipeHandler.BattleRecipe recipe = visibleRecipes.get(i);
                    addRenderableWidget(new RecipeButton(
                            left + 5,
                            relativeY + i * 30,
                            100,
                            20,
                            recipe,
                            WorkbenchScreen.this::onRecipeSelected
                    ));
                }
            }
        };
        addRenderableWidget(recipeList);

        // Craft-Button (Rechts unten)
        addRenderableWidget(new CraftButton(
                leftPos + imageWidth - 50,
                topPos + imageHeight - 30,
                40,
                20,
                Component.literal("Craft"),
                button -> craftItem()
        ));
    }

    private void onRecipeSelected(RecipeHandler.BattleRecipe recipe) {
        this.selectedRecipe = recipe;
        updateMaterialDisplay();
    }

    private void updateMaterialDisplay() {
        // Implementiere Material-Anzeige
    }

    private void craftItem() {
        // Implementiere Crafting-Logik
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}
