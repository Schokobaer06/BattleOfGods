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


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomRecipeRegistry {
    public static void registerRecipes() {
        try {
            Path path = Path.of("assets/battleofgods/recipes/custom/"); // Ordner für deine JSONs
            Files.walk(path).filter(Files::isRegularFile).forEach(file -> {
                try {
                    loadRecipe(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private static void loadRecipe(Path file) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(file)) {
        JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
        
        // Überprüfen des 'type'-Attributs, um nur benutzerdefinierte Rezepte zu laden
        String recipeType = json.get("type").getAsString();
        if (!"battleofgods:custom_recipe".equals(recipeType)) {
            return; // Wenn der Typ nicht übereinstimmt, wird das Rezept ignoriert
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

        // Rezept registrieren
        CustomRecipe.registerRecipe(new CustomRecipe(new ItemStack(output, outputCount), ingredients, craftingStation));
    }
}


}
