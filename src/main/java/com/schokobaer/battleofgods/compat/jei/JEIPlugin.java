// JEIPlugin.java
package com.schokobaer.battleofgods.compat.jei;

import com.schokobaer.battleofgods.compat.jei.category.WorkbenchCategory;
import com.schokobaer.battleofgods.init.InitBlocks;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import com.schokobaer.battleofgods.BattleofgodsMod;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(BattleofgodsMod.MODID, "jei_plugin");
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
}