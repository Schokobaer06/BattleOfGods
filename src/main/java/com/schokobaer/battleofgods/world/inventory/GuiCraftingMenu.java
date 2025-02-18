
package com.schokobaer.battleofgods.world.inventory;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

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
		// Getter für das World-Objekt
	public Level getWorld() {
	    return world;
	}

	// Getter für das Player-Objekt
	public Player getEntity() {
	    return entity;
	}
	
	// Getter für die Koordinaten
	public int getX() {
	    return x;
	}
	
	public int getY() {
	    return y;
	}
	
	public int getZ() {
	    return z;
	}
	
	// Getter für ContainerLevelAccess
	public ContainerLevelAccess getAccess() {
	    return access;
	}
	
	// Getter für den IItemHandler (internal inventory)
	public IItemHandler getInternal() {
	    return internal;
	}
	
	// Getter für den BoundEntity
	public Entity getBoundEntity() {
	    return boundEntity;
	}
	
	// Getter für den BoundBlockEntity
	public BlockEntity getBoundBlockEntity() {
	    return boundBlockEntity;
	}
	
	// Getter für das BoundItemMatcher (falls gesetzt)
	public Supplier<Boolean> getBoundItemMatcher() {
	    return boundItemMatcher;
	}
	
	// Getter für den Bound-Status
	public boolean isBound() {
	    return bound;
	}

}
