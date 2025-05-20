package com.schokobaer.battleofgods.dataGeneration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.handler.RecipeHandler;
import com.schokobaer.battleofgods.handler.RecipeHandler.BattleRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BattleRecipeProvider extends RecipeProvider {

    public BattleRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Hier können Sie Ihre Rezepte hinzufügen
        // Beispiel: consumer.accept(new BattleFinishedRecipe(WorkbenchRecipe("example_recipe", "example_group", List.of(new BattleRecipe.IngredientEntry(Ingredient.of(Items.DIAMOND), 1)), new ItemStack(Items.DIAMOND_SWORD))));
        //consumer.accept(WorkbenchRecipe("example_recipe", "example_group", List.of(new BattleRecipe.IngredientEntry(Ingredient.of(Items.DIAMOND), 1)), new ItemStack(Items.DIAMOND_SWORD)));

    }

    private BattleFinishedRecipe WorkbenchRecipe(String name, String group, List<BattleRecipe.IngredientEntry> ingredients, ItemStack result) {
        return new BattleFinishedRecipe(new BattleRecipe(
                new ResourceLocation(BattleOfGods.MODID, name),
                group, "workbench", false, ingredients, result
        ));
    }

    private record BattleFinishedRecipe(BattleRecipe battleRecipe) implements FinishedRecipe {

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("type", "battleofgods:default_recipe");
            json.addProperty("group", battleRecipe.getGroup());
            json.addProperty("category", battleRecipe.getCategory());
            json.addProperty("replace", battleRecipe.isReplace());

            // Zutaten hinzufügen
            var ingredients = new JsonArray();
            for (BattleRecipe.IngredientEntry entry : battleRecipe.getInputs()) {
                JsonObject ingredientJson = new JsonObject();
                ingredientJson.add("item", entry.ingredient().toJson()); // `toJson()` liefert ein korrektes `JsonElement`
                ingredientJson.addProperty("count", entry.count());
                ingredients.add(ingredientJson);
            }
            json.add("ingredients", ingredients);

            // Ergebnis hinzufügen
            JsonObject result = new JsonObject();
            var resultItem = battleRecipe.getResultItem(null);
            result.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(resultItem.getItem())).toString());
            result.addProperty("count", resultItem.getCount());
            json.add("result", result);
        }

            @Override
            public ResourceLocation getId() {
                return battleRecipe.getId();
            }

            @Override
            public RecipeSerializer<?> getType() {
                return battleRecipe.getSerializer();
            }

            @Override
            public JsonObject serializeAdvancement() {
                return null; // Keine Fortschritte
            }

            @Override
            public ResourceLocation getAdvancementId() {
                return null; // Keine Fortschritte
            }
        }
}