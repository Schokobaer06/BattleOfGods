package com.schokobaer.battleofgods.world.inventory;

import com.schokobaer.battleofgods.init.InitMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class WorkbenchMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final Level world;
    public final Player entity;

    public WorkbenchMenu(int containerId, Inventory playerInv, FriendlyByteBuf data) {
        super(InitMenu.WORKBENCH.get(), containerId);
        this.world = playerInv.player.level();
        this.entity = playerInv.player;
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }

    @Override
    public Map<Integer, Slot> get() {
        return Map.of();
    }
}
