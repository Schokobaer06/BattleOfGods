package com.schokobaer.battleofgods.handler;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WorkbenchRecipeTransferHandler implements IRecipeTransferHandler<WorkbenchMenu, Object> {

    @Override
    public Class<? extends WorkbenchMenu> getContainerClass() {
        return WorkbenchMenu.class;
    }

    @Override
    public Optional<MenuType<WorkbenchMenu>> getMenuType() {
        return Optional.empty();
    }

    @Override
    public RecipeType<Object> getRecipeType() {
        return RecipeType.create(
                BattleofgodsMod.MODID,
                "workbench",
                RecipeHandler.BattleRecipe.class
        );
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(WorkbenchMenu menu, Object recipe, IRecipeSlotsView slots, Player player, boolean maxTransfer, boolean doTransfer) {
        if (doTransfer && recipe instanceof RecipeHandler.BattleRecipe battleRecipe) {
            // Setze das ausgewählte Rezept im Container
            menu.setSelectedRecipe(battleRecipe);
        }
        return null;
    }
}