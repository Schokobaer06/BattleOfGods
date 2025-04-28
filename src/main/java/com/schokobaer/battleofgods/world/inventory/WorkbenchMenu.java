package com.schokobaer.battleofgods.world.inventory;

import com.schokobaer.battleofgods.BattleofgodsMod;
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
    private String recipeGroup;
    private ResourceLocation backgroundLocation = new ResourceLocation("battleofgods:textures/gui/workbench.png");
    private ResourceLocation recipeListLocation = new ResourceLocation("battleofgods:textures/gui/scrollpanels/recipelist.png");
    private ResourceLocation materialListLocation = new ResourceLocation("battleofgods:textures/gui/scrollpanels/materiallist.png");

    public WorkbenchMenu(int containerId, Inventory inv) {
        super(InitMenu.WORKBENCH.get(), containerId);
        this.world = inv.player.level();
        this.player = inv.player;
        this.recipeGroup = "workbench";
        this.access = ContainerLevelAccess.create(world, inv.player.blockPosition());

        for (int col = 0; col < 3; ++col)
            for (int row = 0; row < 9; ++row)
                this.addSlot(new Slot(inv, row + (col + 1) * 9, 8 + row * 18, 84 + col * 18));
        for (int row = 0; row < 9; ++row)
            this.addSlot(new Slot(inv, row, 8 + row * 18, 142));
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
        if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug("WorkbenchMenu - craftItem has been called: Crafting Item: {}", recipeId);
        //RecipeHandler.BattleRecipe temp = getSelectedRecipe();
        Optional<RecipeHandler.BattleRecipe> optionalRecipe = RecipeHandler.getRecipeById(recipeId);
        setSelectedRecipe(optionalRecipe.orElse(null));
        if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug(" WorkbenchMenu - optionalRecipe: {}\nselectedRecipe: {}", optionalRecipe.get().getId(), selectedRecipe.getId());
        RecipeHandler.BattleRecipe recipe = getSelectedRecipe();
        if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug(" WorkbenchMenu - recipe (Check 1): {}", recipe);
        if(recipe == null) return;
        if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug(" WorkbenchMenu - recipe (Check 2): {}", recipe);
        RegistryAccess registryAccess = player.level().registryAccess();
        if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug("WorkbenchMenu - registryAccess: {}", registryAccess);
        //ItemStack result = recipe.assemble((Container) this, registryAccess);
        if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug("WorkbenchMenu - TEST");
        //if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug("WorkbenchMenu - Result: {}", result);


        access.execute((level, pos) -> {
            if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug("WorkbenchMenu - access.execute has been called: level: {}, pos: {}", level, pos);
            if(hasRequiredItems(player) && recipe.matches(player.getInventory(), level)) {
                if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug("WorkbenchMenu - hasRequiredItems: true");
                consumeIngredients(player);
                giveResult(player);
                broadcastChanges();
            }
        });
        //setSelectedRecipe(temp);
    }

    public boolean hasRequiredItems(Player player) {
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
        if (BattleofgodsMod.isDebug()) BattleofgodsMod.LOGGER.debug("WorkbenchMenu - giveResult has been called: giving result: {}", selectedRecipe.getResultItem(player.level().registryAccess()));
        RegistryAccess registryAccess = player.level().registryAccess();
        ItemStack result = selectedRecipe.getResultItem(registryAccess).copy();
        ItemStack carried = player.containerMenu.getCarried();

        if (carried.isEmpty()) {
            // Maus ist leer, setze das Resultat auf die Maus
            player.containerMenu.setCarried(result.copy());
        } else if (ItemStack.isSameItemSameTags(carried, result)) {
            // Gleiches Item auf der Maus, erhöhe die Anzahl
            int newCount = Math.min(carried.getCount() + result.getCount(), carried.getMaxStackSize());
            carried.setCount(newCount);
            player.containerMenu.setCarried(carried);
        } else {
            // Anderes Item auf der Maus, droppe das Resultat
            if (!player.getInventory().add(result)) {
                player.drop(result, false);
            }
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

    public ResourceLocation getGUIBackgroundTexture() {
        return backgroundLocation;
    }
    public ResourceLocation getRecipeListBackgroundLocation() {
        return recipeListLocation;
    }
    public ResourceLocation getMaterialListBackgroundLocation() {
        return materialListLocation;
    }

    public void setGUIBackgroundTexture(ResourceLocation texture) {
        this.backgroundLocation = texture;
    }
    public void setRecipeListBackgroundTexture(ResourceLocation texture) {
        this.recipeListLocation = texture;
    }
    public void setMaterialListBackgroundTextur(ResourceLocation texture) {
        this.materialListLocation = texture;
    }

    public void setRecipeGroup(String recipeGroup) {
        this.recipeGroup = recipeGroup;
    }


}
