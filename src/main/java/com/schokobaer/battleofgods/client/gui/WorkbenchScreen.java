package com.schokobaer.battleofgods.client.gui;

import com.mojang.blaze3d.vertex.Tesselator;
import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.client.widget.MaterialWidget;
import com.schokobaer.battleofgods.client.widget.RecipeButton;
import com.schokobaer.battleofgods.mechanics.recipe.CraftPacket;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class WorkbenchScreen extends AbstractContainerScreen<WorkbenchMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("battleofgods:textures/gui/workbench.png");
    private static final ResourceLocation SCROLLER =
            new ResourceLocation("textures/gui/container/creative_inventory/scroller.png");

    private ScrollPanel recipeList;
    private List<RecipeHandler.BattleRecipe> visibleRecipes;
    private List<MaterialWidget> materialWidgets = new ArrayList<>();

    public WorkbenchScreen(WorkbenchMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();

        // Rezeptliste initialisieren
        assert minecraft != null;
        visibleRecipes = RecipeHandler.getCraftableRecipes(minecraft.player);

        recipeList = new ScrollPanel(minecraft, width / 4, height, 20, height - 50, 10) {

            @Override
            public void updateNarration(NarrationElementOutput p_169152_) {

            }

            @Override
            public NarrationPriority narrationPriority() {
                return NarrationPriority.NONE;
            }

            @Override
            protected int getContentHeight() {
                return visibleRecipes.size() * 18;
            }


            @Override
            protected void drawPanel(GuiGraphics guiGraphics, int x, int y, Tesselator tess, int mouseX, int mouseY) {
                for(int i = 0; i < visibleRecipes.size(); i++) {
                    int yPos = y + i * 18;
                    addRenderableWidget(new RecipeButton(
                            x + 5,
                            yPos,
                            visibleRecipes.get(i),
                            WorkbenchScreen.this::selectRecipe
                    ));
                }
            }
        };
        addRenderableWidget(recipeList);

        // Craft-Button
        addRenderableWidget(new ImageButton(
                leftPos + 200, topPos + 220, 20, 20,
                0, 0, 20,
                new ResourceLocation("battleofgods:textures/gui/hammer_button.png"),
                40, 40,
                button -> craftItem()
        ));
    }

    private void selectRecipe(RecipeHandler.BattleRecipe recipe) {
        menu.setSelectedRecipe(recipe);
        updateMaterialDisplay();
    }

    private void updateMaterialDisplay() {
        // Alte Widgets entfernen
        materialWidgets.forEach(this::removeWidget);
        materialWidgets.clear();

        if(menu.getSelectedRecipe() == null) return;

        // Neue Material-Widgets erstellen
        int yPos = topPos + 50;
        for(RecipeHandler.BattleRecipe.IngredientEntry entry : menu.getSelectedRecipe().getInputs()) {
            MaterialWidget widget = new MaterialWidget(
                    leftPos + 180, yPos,
                    entry, minecraft.player
            );
            materialWidgets.add(widget);
            addRenderableWidget(widget);
            yPos += 25;
        }
    }

    private void craftItem() {
        if (menu.getSelectedRecipe() == null) return;
        BattleofgodsMod.PACKET_HANDLER.sendToServer(new CraftPacket(menu.getSelectedRecipe().getId()));
        init();
    }


    private float getScrollAmount() {
        try {
            Field scrollField = ScrollPanel.class.getDeclaredField("scrollDistance");
            scrollField.setAccessible(true);
            return (float) scrollField.get(recipeList);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Scrollbar rendern
        guiGraphics.blit(SCROLLER,
                leftPos + 120, topPos + 20 + (int)((height - 50) * getScrollAmount()),
                12, 15, 0, 0, 12, 15, 12, 15);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
    /*
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("battleofgods:textures/gui/workbench.png");
    private static final ResourceLocation SCROLLER =
            new ResourceLocation("textures/gui/container/creative_inventory/scroller.png");

    private ScrollPanel recipeList;
    private List<RecipeHandler.BattleRecipe> visibleRecipes;
    private List<MaterialWidget> materialWidgets = new ArrayList<>();

    public WorkbenchScreen(WorkbenchMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();

    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);

        super.render(graphics, mouseX, mouseY, partialTicks);

        this.renderTooltip(graphics, mouseX, mouseY);
    }
    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
    }

    @Override
    protected <T extends GuiEventListener & Renderable & NarratableEntry> @NotNull T addRenderableWidget(T widget) {

        return super.addRenderableWidget(widget);
    }
}

     */