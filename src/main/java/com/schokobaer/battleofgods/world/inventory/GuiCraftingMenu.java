package com.schokobaer.battleofgods.world.inventory;

import com.schokobaer.battleofgods.CustomRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import com.schokobaer.battleofgods.init.BattleofgodsModMenus;


public class GuiCraftingMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final BlockEntity boundBlockEntity;
    private final Player player;
/*
    public GuiCraftingMenu(int id, Inventory playerInventory, BlockEntity blockEntity) {
        super(null, id); // Falls du eine `MenuType`-Referenz hast, ersetze `null`
        this.boundBlockEntity = blockEntity;
        this.player = playerInventory.player;
        this.access = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
    }*/
    public GuiCraftingMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
	    super(BattleofgodsModMenus.GUI_CRAFTING.get(), id);
	    this.entity = inv.player;
	    this.world = inv.player.level();
	    this.internal = new ItemStackHandler(0);
	    BlockPos pos = null;
	    if (extraData != null) {
	        pos = extraData.readBlockPos();
	        this.x = pos.getX();
	        this.y = pos.getY();
	        this.z = pos.getZ();
	        access = ContainerLevelAccess.create(world, pos);
	    }
    }
    

    public Player getPlayer() {
        return this.player;
    }

    public BlockEntity getBoundBlockEntity() {
        return this.boundBlockEntity;
    }

    public void craftItem(ItemStack selectedItem) {
        CustomRecipe recipe = CustomRecipe.getCraftableRecipes(this.player, this.boundBlockEntity.getBlockState().getBlock().asItem())
                .stream()
                .filter(r -> r.getOutput().equals(selectedItem))
                .findFirst()
                .orElse(null);

        if (recipe != null && CustomRecipe.hasIngredients(player, recipe)) {
            CustomRecipe.consumeIngredients(player, recipe);
            player.getInventory().add(selectedItem.copy());
        }
    }
/*
    @Override
    public boolean stillValid(Player player) {
        return this.access.evaluate((level, pos) -> level.getBlockEntity(pos) == this.boundBlockEntity, true);
    }*/
    
    @Override
    public boolean stillValid(Player player) {
        if (this.bound) {
            if (this.boundItemMatcher != null)
                return this.boundItemMatcher.get();
            else if (this.boundBlockEntity != null)
                return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
            else if (this.boundEntity != null)
                return this.boundEntity.isAlive();
        }
        return true;
    }
    @Override
	public ItemStack quickMoveStack(Player player, int index) {
	    return ItemStack.EMPTY; // Falls du noch kein Shift-Klick Handling hast.
	}

}


/*package com.schokobaer.battleofgods.world.inventory;

import com.schokobaer.battleofgods.CustomRecipe;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.Entity;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;

import com.schokobaer.battleofgods.init.BattleofgodsModMenus;

public class GuiCraftingMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public int x, y, z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;

    public GuiCraftingMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(BattleofgodsModMenus.GUI_CRAFTING.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.internal = new ItemStackHandler(0);
        BlockPos pos = null;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            access = ContainerLevelAccess.create(world, pos);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.bound) {
            if (this.boundItemMatcher != null)
                return this.boundItemMatcher.get();
            else if (this.boundBlockEntity != null)
                return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
            else if (this.boundEntity != null)
                return this.boundEntity.isAlive();
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }

    public Map<Integer, Slot> get() {
        return customSlots;
    }

    // Füge Methoden hinzu, um benutzerdefinierte Rezepte hinzuzufügen
    public void addRecipe(CustomRecipe recipe) {
        // Logic to add recipe to the container (slots, etc.)
    }
}
*/