// WorkbenchCategory.java
package com.schokobaer.battleofgods.compat.jei.category;

import com.schokobaer.battleofgods.init.BattleofgodsModBlocks;
import com.schokobaer.battleofgods.init.BattleofgodsModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler.BattleRecipe;

public class WorkbenchCategory implements IRecipeCategory<BattleRecipe> {
    public static final RecipeType<BattleRecipe> TYPE = RecipeType.create(
            BattleofgodsMod.MODID,
            "workbench",
            BattleRecipe.class
    );

    private final IDrawable background;
    private final IDrawable icon;

    public WorkbenchCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(
                new ResourceLocation(BattleofgodsMod.MODID, "textures/gui/workbench.png"),
                8, 15, 160, 60
        );
        this.icon = guiHelper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK,
                BattleofgodsModItems.WOODEN_WORKBENCH.get().asItem().getDefaultInstance()
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
    public void setRecipe(IRecipeLayoutBuilder builder, BattleRecipe recipe, IFocusGroup focuses) {
        // Inputs
        for (int i = 0; i < recipe.getInputs().size(); i++) {
            var entry = recipe.getInputs().get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, (i % 3) * 18 + 1, (i / 3) * 18 + 1)
                    .addIngredients(entry.ingredient())
                    .addTooltipCallback((recipeSlotView, tooltip) ->
                            tooltip.add(Component.literal(entry.count() + "x"))
                    );
        }

        // Output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 120, 19)
                .addItemStack(recipe.getOutput());
    }
}
