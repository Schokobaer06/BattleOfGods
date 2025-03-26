package com.schokobaer.battleofgods.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.schokobaer.battleofgods.world.inventory.TestGuiMenu;

import com.mojang.blaze3d.systems.RenderSystem;

public class TestGuiScreen extends AbstractContainerScreen<TestGuiMenu> {
	private final static HashMap<String, Object> guistate = TestGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_test_button;
	Button button_test_button_undecorated;
	ImageButton imagebutton_gui_test;

	public TestGuiScreen(TestGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("battleofgods:textures/screens/test_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (mouseX > leftPos + 38 && mouseX < leftPos + 62 && mouseY > topPos + 43 && mouseY < topPos + 67)
			guiGraphics.renderTooltip(font, Component.translatable("gui.battleofgods.test_gui.tooltip_test_tooltip"), mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		guiGraphics.blit(new ResourceLocation("battleofgods:textures/screens/gui_test.png"), this.leftPos + 55, this.topPos + 4, 0, 0, 88, 83, 88, 83);

		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.battleofgods.test_gui.label_test_text"), 8, 5, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_test_button = Button.builder(Component.translatable("gui.battleofgods.test_gui.button_test_button"), e -> {
		}).bounds(this.leftPos + -10, this.topPos + 15, 82, 20).build();
		guistate.put("button:button_test_button", button_test_button);
		this.addRenderableWidget(button_test_button);
		button_test_button_undecorated = new PlainTextButton(this.leftPos + -19, this.topPos + 95, 145, 20, Component.translatable("gui.battleofgods.test_gui.button_test_button_undecorated"), e -> {
		}, this.font);
		guistate.put("button:button_test_button_undecorated", button_test_button_undecorated);
		this.addRenderableWidget(button_test_button_undecorated);
		imagebutton_gui_test = new ImageButton(this.leftPos + 118, this.topPos + 2, 176, 166, 0, 0, 166, new ResourceLocation("battleofgods:textures/screens/atlas/imagebutton_gui_test.png"), 176, 332, e -> {
		});
		guistate.put("button:imagebutton_gui_test", imagebutton_gui_test);
		this.addRenderableWidget(imagebutton_gui_test);
	}
}
