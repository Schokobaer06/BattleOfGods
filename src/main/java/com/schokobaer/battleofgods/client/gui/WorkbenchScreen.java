package com.schokobaer.battleofgods.client.gui;

import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class WorkbenchScreen extends AbstractContainerScreen<WorkbenchMenu> {
    public WorkbenchScreen(WorkbenchMenu workbenchMenu, Inventory inventory, Component component) {
        super(workbenchMenu,inventory,component);
        this.titleLabelX = 8;
        this.inventoryLabelX = 10;
    }

    @Override
    protected void renderBg(GuiGraphics p_283065_, float p_97788_, int p_97789_, int p_97790_) {

    }

    @Override
    protected void init() {
        super.init();
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