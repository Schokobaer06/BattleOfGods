package com.schokobaer.battleofgods.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.schokobaer.battleofgods.world.inventory.GuiCraftingMenu;
import com.schokobaer.battleofgods.CustomRecipe;
import java.util.List;
import net.minecraft.world.level.block.entity.BlockEntity;


public class GuiCraftingScreen extends AbstractContainerScreen<GuiCraftingMenu> {
    private final Level world;
    private final int x, y, z;
    private ItemStack selectedItem = ItemStack.EMPTY;
    private Button craftButton;
    private List<CustomRecipe> craftableRecipes;

    private static final ResourceLocation texture = new ResourceLocation("battleofgods:textures/screens/gui_crafting.png");

    public GuiCraftingScreen(GuiCraftingMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
    }

    @Override
    protected void init() {
        super.init();
        craftableRecipes = CustomRecipe.getCraftableRecipes(this.menu.entity, this.menu.boundBlockEntity.getBlockState().getBlock().asItem());
        int buttonY = this.topPos + 10;

        for (CustomRecipe recipe : craftableRecipes) {
            ItemStack output = recipe.getOutput();
			this.addRenderableWidget(Button.builder(Component.literal(""), button -> selectItem(output))
			    .pos(this.leftPos + 10, buttonY)
			    .size(20, 20)
			    .build());
            buttonY += 22;
        }

        craftButton = this.addRenderableWidget(new Button(this.leftPos + 130, this.topPos + 80, 30, 20, Component.literal("🔨"), button -> craftItem()));
        updateCraftButton();
    }

    private void selectItem(ItemStack item) {
        this.selectedItem = item;
        updateCraftButton();
    }

    private void craftItem() {
        if (!selectedItem.isEmpty()) {
            CustomRecipe recipe = craftableRecipes.stream().filter(r -> r.getOutput().equals(selectedItem)).findFirst().orElse(null);
            if (recipe != null) {
                CustomRecipe.craftItem(this.menu.entity, recipe);
                selectedItem = ItemStack.EMPTY;
                craftableRecipes = CustomRecipe.getCraftableRecipes(this.menu.entity, this.menu.boundBlockEntity.getBlockState().getBlock().asItem());
                updateCraftButton();
            }
        }
    }

    private void updateCraftButton() {
        boolean canCraft = !selectedItem.isEmpty() && CustomRecipe.hasIngredients(this.menu.entity, selectedItem);
        craftButton.active = canCraft;
        craftButton.setFGColor(canCraft ? 0x00FF00 : 0xFF0000);
    }
    
    @Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
	    guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.width, this.height);
	}


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
	public BlockEntity getBoundBlockEntity() {
	    return boundBlockEntity;
	}
    
}
