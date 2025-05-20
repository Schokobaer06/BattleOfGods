// WorkbenchCategory.java
package com.schokobaer.battleofgods.compat.jei.category;
/*
import com.mojang.blaze3d.systems.RenderSystem;
import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.handler.RecipeHandler.BattleRecipe;
import com.schokobaer.battleofgods.init.InitBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class WorkbenchCategory implements IRecipeCategory<BattleRecipe> {
    public static final RecipeType<BattleRecipe> TYPE = RecipeType.create(
            BattleOfGods.MODID,
            "workbench",
            BattleRecipe.class
    );

    private final IDrawable background;
    private final IDrawable icon;

    public WorkbenchCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(new ResourceLocation(BattleOfGods.MODID, "textures/gui/crafting_station/workbench.png"),
                8, 15, 160, 60).setTextureSize(176, 166).build();

        this.icon = guiHelper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK,
                InitBlocks.WOODEN_WORKBENCH.get().asItem().getDefaultInstance()
        );
    }


    @Override
    public RecipeType<BattleRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.battleofgods.wooden_workbench");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(BattleRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        RenderSystem.disableBlend();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BattleRecipe recipe, IFocusGroup focuses) {
        // Inputs
        for (int i = 0; i < recipe.getInputs().size(); i++) {
            var entry = recipe.getInputs().get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, (i % 6) * 18 + 1, (i / 6) * 18 + 1)
                    .addIngredients(entry.ingredient())
                    .addTooltipCallback((recipeSlotView, tooltip) ->
                            tooltip.add(Component.literal(entry.count() + "x"))
                    );
        }

        // Output
        if (recipe.getOutput().getCount() > 1)
            builder.addSlot(RecipeIngredientRole.OUTPUT, 120, 19)
                    .addItemStack(recipe.getOutput())
                    .addTooltipCallback((recipeSlotView, tooltip) ->
                            tooltip.add(Component.literal(recipe.getOutput().getCount() + "x"))
                    );
        else
            builder.addSlot(RecipeIngredientRole.OUTPUT, 120, 19)
                    .addItemStack(recipe.getOutput());


        builder.moveRecipeTransferButton(120, 45);
    }
}*/
