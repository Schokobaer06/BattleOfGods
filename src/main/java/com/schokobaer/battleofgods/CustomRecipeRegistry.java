
package com.schokobaer.battleofgods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.HashMap;
import net.minecraftforge.fml.common.Mod;
import java.io.BufferedReader;
import com.schokobaer.battleofgods.CustomRecipe;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomRecipeRegistry {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        registerRecipes(); // Rezepte beim Spielstart laden
    }

    public static void registerRecipes() {
        try {
            Path path = Path.of("assets/battleofgods/recipes/custom/");
            if (!Files.exists(path)) {
                System.out.println("Recipe directory does not exist: " + path.toAbsolutePath());
                return;
            }

            Files.walk(path).filter(Files::isRegularFile).forEach(file -> {
                try {
                    loadRecipe(file);
                } catch (IOException e) {
                    System.err.println("Error loading recipe from file: " + file);
                    e.printStackTrace();
                }
            });

            System.out.println("Custom recipes loaded successfully!");

        } catch (IOException e) {
            System.err.println("Error reading recipe directory.");
            e.printStackTrace();
        }
    }

    private static void loadRecipe(Path file) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            
            String recipeType = json.get("type").getAsString();
            if (!"battleofgods:custom_recipe".equals(recipeType)) {
                return;
            }

            JsonObject outputJson = json.getAsJsonObject("output");
            String outputItem = outputJson.get("item").getAsString();
            int outputCount = outputJson.get("count").getAsInt();
            Item output = ForgeRegistries.ITEMS.getValue(new ResourceLocation(outputItem));

            JsonArray ingredientsJson = json.getAsJsonArray("ingredients");
            Map<Item, Integer> ingredients = new HashMap<>();
            for (JsonElement element : ingredientsJson) {
                JsonObject ingredient = element.getAsJsonObject();
                String itemName = ingredient.get("item").getAsString();
                int count = ingredient.get("count").getAsInt();
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
                ingredients.put(item, count);
            }

            String craftingStationName = json.get("crafting_station").getAsString();
            Item craftingStation = ForgeRegistries.ITEMS.getValue(new ResourceLocation(craftingStationName));

            CustomRecipe.registerRecipe(new CustomRecipe(new ItemStack(output, outputCount), ingredients, craftingStation));
            System.out.println("Loaded recipe for: " + outputItem);
        }
    }
}

