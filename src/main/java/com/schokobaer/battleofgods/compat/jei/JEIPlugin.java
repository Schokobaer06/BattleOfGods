// JEIPlugin.java
package com.schokobaer.battleofgods.compat.jei;
/*
import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.compat.jei.category.WorkbenchCategory;
import com.schokobaer.battleofgods.handler.RecipeHandler;
import com.schokobaer.battleofgods.handler.WorkbenchRecipeTransferHandler;
import com.schokobaer.battleofgods.init.InitBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(BattleOfGods.MODID, "jei_plugin");
    }

    // 1. Registriere die Rezeptkategorie
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WorkbenchCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    // 2. Registriere die Rezepte
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(
                WorkbenchCategory.TYPE,
                RecipeHandler.getRecipesByGroup("workbench")
        );
    }

    // 3. Registriere den Workbench-Block als Katalysator
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(InitBlocks.WOODEN_WORKBENCH.get()), // Ersetze mit deinem Block
                WorkbenchCategory.TYPE
        );
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                new WorkbenchRecipeTransferHandler(), RecipeType.create(
                        BattleOfGods.MODID,
                        "workbench",
                        RecipeHandler.BattleRecipe.class
                )
        );
    }
}

 */