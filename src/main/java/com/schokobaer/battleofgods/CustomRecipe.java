/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside com.schokobaer.battleofgods as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package com.schokobaer.battleofgods;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.schokobaer.battleofgods.init.BattleofgodsModBlocks;
import com.schokobaer.battleofgods.init.BattleofgodsModItems;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomRecipe {
    private static final List<CustomRecipe> RECIPES = new ArrayList<>();
    
    private final ItemStack output;
    private final Map<Item, Integer> ingredients;
    private final Item craftingStation;	
    
	public CustomRecipe(ItemStack output, Map<Item, Integer> ingredients, Item craftingStation) {
		this.output = output;
        this.ingredients = ingredients;
        this.craftingStation = craftingStation;
	}
	public static void registerRecipe(CustomRecipe recipe) {
        RECIPES.add(recipe);
    }
    public static List<CustomRecipe> getCraftableRecipes(net.minecraft.world.entity.player.Player player, Item station) {
        List<CustomRecipe> craftable = new ArrayList<>();
        for (CustomRecipe recipe : RECIPES) {
            if (recipe.craftingStation.equals(station) && hasIngredients(player, recipe)) {
                craftable.add(recipe);
            }
        }
        return craftable;
    }
	public static boolean hasIngredients(net.minecraft.world.entity.player.Player player, CustomRecipe recipe) {
        Map<Item, Integer> inventoryCount = new HashMap<>();
        
        for (ItemStack stack : player.getInventory().items) {
            inventoryCount.put(stack.getItem(), inventoryCount.getOrDefault(stack.getItem(), 0) + stack.getCount());
        }

        for (Map.Entry<Item, Integer> entry : recipe.ingredients.entrySet()) {
            if (inventoryCount.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
     public static void craftItem(net.minecraft.world.entity.player.Player player, CustomRecipe recipe) {
        if (!hasIngredients(player, recipe)) return;

        for (Map.Entry<Item, Integer> entry : recipe.ingredients.entrySet()) {
            removeItems(player, entry.getKey(), entry.getValue());
        }
       player.getInventory().add(recipe.output.copy());
    }
    private static void removeItems(net.minecraft.world.entity.player.Player player, Item item, int amount) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == item) {
                int toRemove = Math.min(amount, stack.getCount());
                stack.shrink(toRemove);
                amount -= toRemove;
                if (amount <= 0) return;
            }
        }
    }
    public static void consumeIngredients(net.minecraft.world.entity.player.Player player, CustomRecipe recipe){
        for (Map.Entry<Item, Integer> entry : recipe.ingredients.entrySet()) {
            removeItems(player, entry.getKey(), entry.getValue());
        }
    }
    public ItemStack getOutput() {
    return this.output;
}

    
	/*
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		new CustomRecipe();
	}

	@Mod.EventBusSubscriber
	private static class CustomRecipeForgeBusEvents {
		@SubscribeEvent
		public static void serverLoad(ServerStartingEvent event) {
		}
		

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void clientLoad(FMLClientSetupEvent event) {
		}
	}*/
}
