// JEIPlugin.java
package com.schokobaer.battleofgods.compat.jei;

import com.schokobaer.battleofgods.compat.jei.category.WorkbenchCategory;
import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import net.minecraft.client.Minecraft;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(BattleofgodsMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WorkbenchCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var level = Minecraft.getInstance().level;
        if (level != null) {
            registration.addRecipes(
                    WorkbenchCategory.TYPE,
                    RecipeHandler.getRecipesByGroup("workbench")
            );
        }
    }
}