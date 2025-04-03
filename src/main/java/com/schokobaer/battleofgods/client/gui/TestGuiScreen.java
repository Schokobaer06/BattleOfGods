package com.schokobaer.battleofgods.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.schokobaer.battleofgods.world.inventory.TestGuiMenu;

import com.mojang.blaze3d.systems.RenderSystem;

public class TestGuiScreen extends AbstractContainerScreen<TestGuiMenu> {
	private final static HashMap<String, Object> guistate = TestGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	ImageButton imagebutton_crafting_hammer;
	ImageButton imagebutton_recipe_button;
	ImageButton imagebutton_recipe_button1;

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
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
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
	}

	@Override
	public void init() {
		super.init();
		imagebutton_crafting_hammer = new ImageButton(
				this.leftPos + 142, this.topPos + 53,
				27, 27,
				0, 0,
				27,
				new ResourceLocation("battleofgods:textures/screens/atlas/imagebutton_crafting_hammer.png"),
				27, 54,
				e -> {
		});
		guistate.put("button:imagebutton_crafting_hammer", imagebutton_crafting_hammer);
		this.addRenderableWidget(imagebutton_crafting_hammer);
		imagebutton_recipe_button = new ImageButton(this.leftPos + 8, this.topPos + 4, 16, 16, 0, 0, 16, new ResourceLocation("battleofgods:textures/screens/atlas/imagebutton_recipe_button.png"), 16, 32, e -> {
		});
		guistate.put("button:imagebutton_recipe_button", imagebutton_recipe_button);
		this.addRenderableWidget(imagebutton_recipe_button);
		imagebutton_recipe_button1 = new ImageButton(this.leftPos + 8, this.topPos + 66, 16, 16, 0, 0, 16, new ResourceLocation("battleofgods:textures/screens/atlas/imagebutton_recipe_button1.png"), 16, 32, e -> {
		});
		guistate.put("button:imagebutton_recipe_button1", imagebutton_recipe_button1);
		this.addRenderableWidget(imagebutton_recipe_button1);
	}
}
