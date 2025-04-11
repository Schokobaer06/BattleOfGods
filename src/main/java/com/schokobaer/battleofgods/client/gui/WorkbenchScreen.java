package com.schokobaer.battleofgods.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.client.widget.MaterialWidget;
import com.schokobaer.battleofgods.client.widget.RecipeButton;
import com.schokobaer.battleofgods.mechanics.recipe.CraftPacket;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ScrollPanel;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchScreen extends AbstractContainerScreen<WorkbenchMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("battleofgods:textures/gui/workbench.png");
    private static final ResourceLocation SCROLLER =
            new ResourceLocation("battleofgods:textures/gui/scroller.png");

    private ScrollPanel recipeList;
    private List<RecipeHandler.BattleRecipe> visibleRecipes;
    private final List<MaterialWidget> materialWidgets = new ArrayList<>();

    public WorkbenchScreen(WorkbenchMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        //this.playerInventoryTitle.getStyle().withColor(0xD3D3D3);
    }

    @Override
    protected void init() {
        super.init();
        String group = menu.getRecipeGroup();

        // Rezeptliste initialisieren
        assert minecraft != null;
        //visibleRecipes = RecipeHandler.getCraftableRecipes(minecraft.player);
        visibleRecipes = RecipeHandler.getCraftableRecipesByGroup(minecraft.player, group);

        List<RecipeButton> buttons = new ArrayList<>();
        visibleRecipes.forEach(recipe -> {
            buttons.add(new RecipeButton(
                    0,
                    0,
                    recipe,
                    this::selectRecipe
            ){
                @Override
                public void onClick(double x, double y) {
                    //onSelect(x,y);
                    Minecraft.getInstance().getSoundManager().play(
                            SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    BattleofgodsMod.LOGGER.debug("Button clicked: {}", recipe.getId());
                }

            });
        });
        BattleofgodsMod.LOGGER.debug("Recipes: {}", visibleRecipes.size());
        visibleRecipes.forEach(recipe -> {
            BattleofgodsMod.LOGGER.debug("Recipe: {}", recipe.getId());
        });
        // Recipe List

        recipeList = new ScrollPanel(minecraft,
                (imageWidth / 2) - 2,
                (imageHeight / 2) - (font.lineHeight * 3 + 3),
                topPos + font.lineHeight + 8, leftPos,
                1,
                3,
                0x00FFFFFF,
                0x00FFFFFF
        ) {
            @Override
            public void updateNarration(NarrationElementOutput p_169152_) {

            }

            @Override
            public NarrationPriority narrationPriority() {
                return NarrationPriority.NONE;
            }

            @Override
            protected int getContentHeight() {
                if (buttons.isEmpty()) return 0;
                int buttonHeight = buttons.get(0).getHeight(); // Höhe eines Buttons
                int buttonSpacing = 2; // Abstand zwischen Buttons
                int buttonsPerRow = Math.max(1, (width - buttonSpacing) / (buttonHeight + buttonSpacing));
                int rowCount = (int) Math.ceil((double) buttons.size() / buttonsPerRow);
                return rowCount * (buttonHeight + buttonSpacing);
            }


            @Override
            protected void drawPanel(GuiGraphics guiGraphics, int x, int y, Tesselator tess, int mouseX, int mouseY) {

                if (buttons.isEmpty()) return;

                int buttonWidth = buttons.get(0).getWidth(); // Breite eines Buttons
                int buttonHeight = buttons.get(0).getHeight(); // Höhe eines Buttons
                int buttonSpacing = 2; // Abstand zwischen Buttons
                int buttonsPerRow = Math.max(1, (width - buttonSpacing) / (buttonWidth + buttonSpacing));

                int rowWidth = buttonsPerRow * (buttonWidth + buttonSpacing) - buttonSpacing;
                int startX = (width - rowWidth) / 2; // Zentrierte Position
                guiGraphics.pose().pushPose();
                guiGraphics.enableScissor(left, top, left + width, top + height);


                //Adding buttons to the scroll panel
                for (int i = 0; i < buttons.size(); i++) {
                    RecipeButton button = buttons.get(i);
                    int row = i / buttonsPerRow;
                    int col = i % buttonsPerRow;

                    int buttonX = (x - width) + (startX + col * (buttonWidth + buttonSpacing));
                    int buttonY = y + (row * (buttonHeight + buttonSpacing));
                    button.setX(buttonX);
                    button.setY(buttonY);
                    assert minecraft != null;
                    button.render(guiGraphics, x, y, minecraft.getFrameTime());
                }
                guiGraphics.disableScissor();

                buttons.forEach(btn -> {
                    if (isMouseOver(btn, mouseX, mouseY))
                        btn.renderTooltip(guiGraphics, mouseX, mouseY);
                });
            }

            //making buttons clickable
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (!isMouseOver(mouseX, mouseY)) {
                    return false;
                }

                // Buttons prüfen
                for (RecipeButton btn : buttons) {

                    if (mouseX >= btn.getX() &&
                            mouseX <= btn.getX() + btn.getWidth() &&
                            mouseY >= btn.getY() &&
                            mouseY <= btn.getY() + btn.getHeight()) {
                        btn.mouseClicked(mouseX,mouseY,button);
                    }

                }
                return super.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean isMouseOver(double mouseX, double mouseY) {
                for (RecipeButton btn : buttons) {
                    if (mouseX >= btn.getX() &&
                            mouseX <= btn.getX() + btn.getWidth() &&
                            mouseY >= btn.getY() &&
                            mouseY <= btn.getY() + btn.getHeight() &&
                            mouseX >= left && mouseX <= left + width &&
                            mouseY >= top && mouseY <= top + height
                    ) {
                        return btn.isMouseOver(mouseX, mouseY);
                    }
                }
                return false;

            }

            public boolean isMouseOver(RecipeButton button, double mouseX, double mouseY) {
                return mouseX >= button.getX() &&
                        mouseX <= button.getX() + button.getWidth() &&
                        mouseY >= button.getY() &&
                        mouseY <= button.getY() + button.getHeight() &&
                        mouseX >= left && mouseX <= left + width &&
                        mouseY >= top && mouseY <= top + height;
            }
        };
        // Craft-Button

        addRenderableWidget(new ImageButton(
                7 * (leftPos + imageWidth) / 8, topPos + 64,
                16, 16,
                0, 0,  // Normal state
                16,     // xDiff between states
                new ResourceLocation("battleofgods:textures/gui/crafting_hammer.png"),
                32, 16,
                button -> {
                    Minecraft.getInstance().getSoundManager().play(
                            SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    //craftItem();
                }
        ) {
            @Override
            public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
                int xOffset = this.isHovered() ? 16 : 0; // Hover/Pressed share same texture

                gui.blit(
                        this.resourceLocation,
                        this.getX(), this.getY(),
                        xOffset, 0,
                        this.width, this.height,
                        32, 16
                );
            }
        }).setTooltip(Tooltip.create(Component.translatable("gui.battleofgods.tooltip.craft_hammer")));
        addRenderableWidget(recipeList);
    }

    private void selectRecipe(RecipeHandler.BattleRecipe recipe) {
        menu.setSelectedRecipe(recipe);
        updateMaterialDisplay();
    }

    private void updateMaterialDisplay() {
        // Alte Widgets entfernen
        materialWidgets.forEach(this::removeWidget);
        materialWidgets.clear();

        if (menu.getSelectedRecipe() == null) {
        }

        // Neue Material-list
        /*
        int yPos = topPos + 50;
        for(RecipeHandler.BattleRecipe.IngredientEntry entry : menu.getSelectedRecipe().getInputs()) {
            MaterialWidget widget = new MaterialWidget(
                    leftPos + 130, yPos,
                    entry, minecraft.player
            );
            materialWidgets.add(widget);
            addRenderableWidget(widget);
            yPos += 25;
        }
         */
    }

    private void craftItem() {
        if (menu.getSelectedRecipe() == null) return;
        BattleofgodsMod.PACKET_HANDLER.sendToServer(new CraftPacket(menu.getSelectedRecipe().getId()));
        init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        // Scrollbar #1 for Recipe List
        /*
        guiGraphics.blit(SCROLLER,
                //leftPos + 120, topPos + 20 + (int)((height - 50) * getScrollAmount()),
                leftPos + 120, topPos + 18,
                //12, 15, 0, 0, 12, 15, 12, 15);
                0, 0, 12, 15, 12, 15);

         */
        // Scrollbar #2 for Material List

        RenderSystem.disableBlend();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        // Draw player inventory title
        guiGraphics.drawString(
                this.font,
                this.playerInventoryTitle,
                this.inventoryLabelX, this.inventoryLabelY,
                0x00FFFFFF, // White color (RGB: 255,255,255)
                false // Don't drop shadow
        );
    }
}
