package com.schokobaer.battleofgods.world.inventory;

import com.schokobaer.battleofgods.init.InitMenu;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class WorkbenchMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final Level world;
    public final Player player;
    private RecipeHandler.BattleRecipe selectedRecipe;
    private ContainerLevelAccess access;
    private final String recipeGroup;

    public WorkbenchMenu(int containerId, Inventory inv) {
        super(InitMenu.WORKBENCH.get(), containerId);
        this.world = inv.player.level();
        this.player = inv.player;
        this.recipeGroup = "workbench";

/*
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 0 + 8 + sj * 18, 0 + 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 0 + 8 + si * 18, 0 + 142));
 */
        // Player Inventory (9x3)
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 142 + row * 18));
            }
        }

        // Hotbar (9x1)
        for(int col = 0; col < 9; ++col) {
            addSlot(new Slot(inv, col, 8 + col * 18, 200));
        }
    }

    public String getRecipeGroup() {
        return recipeGroup;
    }

    public void setSelectedRecipe(RecipeHandler.BattleRecipe recipe) {
        this.selectedRecipe = recipe;
    }

    public RecipeHandler.BattleRecipe getSelectedRecipe() {
        return selectedRecipe;
    }

    public void craftItem(Player player, ResourceLocation recipeId) {
        RecipeHandler.BattleRecipe temp = getSelectedRecipe();
        Optional<RecipeHandler.BattleRecipe> optionalRecipe = RecipeHandler.getRecipeById(recipeId);
        setSelectedRecipe(optionalRecipe.orElse(null));
        RecipeHandler.BattleRecipe recipe = getSelectedRecipe();
        if(recipe == null) return;
        RegistryAccess registryAccess = player.level().registryAccess();
        ItemStack result = recipe.assemble((Container) this, registryAccess);


        access.execute((level, pos) -> {
            if(hasRequiredItems(player) && recipe.matches(player.getInventory(), level)) {
                consumeIngredients(player);
                giveResult(player);
                broadcastChanges();
            }
        });
        setSelectedRecipe(temp);
    }

    private boolean hasRequiredItems(Player player) {
        for(RecipeHandler.BattleRecipe.IngredientEntry entry : selectedRecipe.getInputs()) {
            int count = 0;
            for(ItemStack stack : player.getInventory().items) {
                if(entry.ingredient().test(stack)) count += stack.getCount();
                if(count >= entry.count()) break;
            }
            if(count < entry.count()) return false;
        }
        return true;
    }

    private void consumeIngredients(Player player) {
        for(RecipeHandler.BattleRecipe.IngredientEntry entry : selectedRecipe.getInputs()) {
            int remaining = entry.count();
            for(ItemStack stack : player.getInventory().items) {
                if(entry.ingredient().test(stack)) {
                    int remove = Math.min(remaining, stack.getCount());
                    stack.shrink(remove);
                    remaining -= remove;
                    if(remaining <= 0) break;
                }
            }
        }
    }

    private void giveResult(Player player) {
        RegistryAccess registryAccess = player.level().registryAccess();
        if (!player.getInventory().add(selectedRecipe.getResultItem(registryAccess))) {
            player.drop(selectedRecipe.getResultItem(registryAccess), false);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        //return stillValid(access, player, BattleofgodsModBlocks.WOODEN_WORKBENCH.get());
        return player.isAlive();
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 36) { // Player inventory slots (0-35)
                if (!this.moveItemStackTo(itemstack1, 36, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 36, false)) { // Container slots (36+)
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public Map<Integer, Slot> get() {
        return Map.of();
    }

    /*
    public void craftItem(Player player, RecipeHandler.BattleRecipe recipe) {
        if (player.level().isClientSide) return;

        // 1. Prüfe Materialien
        if (!hasEnoughMaterials(player, recipe)) {
            player.displayClientMessage(Component.literal("Nicht genug Materialien!"), true);
            return;
        }

        // 2. Entferne Materialien
        removeMaterials(player, recipe);

        // 3. Füge Ergebnis hinzu
        ItemStack result = recipe.getResultItem(player.level().registryAccess());
        if (!player.getInventory().add(result.copy())) {
            player.drop(result.copy(), false);
        }

        // 4. Aktualisiere Inventar
        player.inventoryMenu.broadcastChanges();
    }

    private boolean hasEnoughMaterials(Player player, RecipeHandler.BattleRecipe recipe) {
        for (RecipeHandler.BattleRecipe.IngredientEntry entry : recipe.getInputs()) {
            if (getAvailableCount(player, entry) < entry.count()) {
                return false;
            }
        }
        return true;
    }

    private void removeMaterials(Player player, RecipeHandler.BattleRecipe recipe) {
        for (RecipeHandler.BattleRecipe.IngredientEntry entry : recipe.getInputs()) {
            int remaining = entry.count();

            for (ItemStack stack : player.getInventory().items) {
                if (entry.ingredient().test(stack)) {
                    int toRemove = Math.min(remaining, stack.getCount());
                    stack.shrink(toRemove);
                    remaining -= toRemove;

                    if (remaining <= 0) break;
                }
            }
        }
    }
    private int getAvailableCount() {
        int count = 0;
        for (ItemStack stack : entity.getInventory().items) {
            if (entry.ingredient().test(stack)) {
                count += stack.getCount();
                if (count >= entry.count()) break;
            }
        }
        return count;
    }*/
    /*
    public void craftItem(Player player, RecipeHandler.BattleRecipe recipe) {
        if (!hasEnoughMaterials(player, recipe)) return;

        removeMaterials(player, recipe);
        giveResult(player, recipe);
    }

    private boolean hasEnoughMaterials(Player player, RecipeHandler.BattleRecipe recipe) {
        for (RecipeHandler.BattleRecipe.IngredientEntry entry : recipe.getInputs()) {
            int available = 0;
            for (ItemStack stack : player.getInventory().items) {
                if (entry.ingredient().test(stack)) {
                    available += stack.getCount();
                    if (available >= entry.count()) break;
                }
            }
            if (available < entry.count()) return false;
        }
        return true;
    }

    private void removeMaterials(Player player, RecipeHandler.BattleRecipe recipe) {
        for (RecipeHandler.BattleRecipe.IngredientEntry entry : recipe.getInputs()) {
            int remaining = entry.count();

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (entry.ingredient().test(stack)) {
                    int remove = Math.min(remaining, stack.getCount());
                    stack.shrink(remove);
                    remaining -= remove;
                    if (remaining <= 0) break;
                }
            }
        }
    }

    private void giveResult(Player player, RecipeHandler.BattleRecipe recipe) {
        RegistryAccess registryAccess = player.level().registryAccess();
        ItemStack result = recipe.getResultItem(registryAccess).copy();

        if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }
        player.inventoryMenu.broadcastChanges();
    }

 */

}
