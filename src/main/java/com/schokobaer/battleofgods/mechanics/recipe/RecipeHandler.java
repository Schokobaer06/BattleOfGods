package com.schokobaer.battleofgods.mechanics.recipe;

import com.google.gson.*;
import com.schokobaer.battleofgods.BattleofgodsMod;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.util.datafix.fixes.BlockEntitySignTextStrictJsonFix.GSON;

public class RecipeHandler {
    public static final List<BattleRecipe> RECIPES = new ArrayList<>();


    public static void loadRecipes() {
        //RECIPES.clear();
        Path recipeDir = Paths.get("data/battleofgods/recipes");

        if (!Files.exists(recipeDir)) return;

        try (var files = Files.walk(recipeDir)) {
            files.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try (FileReader reader = new FileReader(path.toFile())) {
                            BattleRecipe recipe = GSON.fromJson(reader, BattleRecipe.class);
                            if (recipe != null && recipe.isValid()) {
                                RECIPES.add(recipe);
                            }
                        } catch (Exception e) {
                            BattleofgodsMod.LOGGER.error("Fehler beim Laden von Rezept: {}", path, e);
                        }
                    });
        } catch (IOException e) {
            BattleofgodsMod.LOGGER.error("Rezeptverzeichnis nicht gefunden!", e);
        }
    }

    public static List<BattleRecipe> getCraftableRecipes(Player player) {
        return RECIPES.stream()
                .filter(recipe -> hasRequiredItems(player, recipe))
                .collect(Collectors.toList());
    }

    private static boolean hasRequiredItems(Player player, BattleRecipe recipe) {
        for (BattleRecipe.IngredientEntry entry : recipe.getInputs()) {
            int required = entry.count();
            int available = 0;

            for (ItemStack stack : player.getInventory().items) {
                if (entry.ingredient().test(stack)) {
                    available += stack.getCount();
                    if (available >= required) break;
                }
            }

            if (available < required) return false;
        }
        return true;
    }

    public static class BattleRecipe implements Recipe<Container> {
        private final ResourceLocation id;
        private final String group;
        private final String category;
        private final boolean replace;
        private final List<IngredientEntry> inputs;
        private final ItemStack output;
        public static final Serializer SERIALIZER = new Serializer();

        public BattleRecipe(ResourceLocation id, String group, String category, boolean replace, List<IngredientEntry> inputs, ItemStack output) {
            this.id = id;
            this.group = group;
            this.category = category;
            this.replace = replace;
            this.inputs = inputs;
            this.output = output;
        }

        public boolean isValid() {
            return output != null && !output.isEmpty() && inputs.stream().allMatch(i -> !i.ingredient().isEmpty());
        }

        @Override
        public boolean matches(Container container, Level level) {
            int[] required = inputs.stream().mapToInt(IngredientEntry::count).toArray();

            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack stack = container.getItem(i);
                if (stack.isEmpty()) continue;

                for (int j = 0; j < inputs.size(); j++) {
                    IngredientEntry entry = inputs.get(j);
                    if (entry.ingredient().test(stack)) {
                        int reduce = Math.min(required[j], stack.getCount());
                        required[j] -= reduce;
                        if (required[j] == 0) break;
                    }
                }
            }

            return Arrays.stream(required).allMatch(c -> c <= 0);
        }

        @Override
        public ItemStack assemble(Container container, RegistryAccess registryAccess) {
            return output.copy();
        }

        @Override
        public boolean canCraftInDimensions(int width, int height) {
            return true;
        }

        @Override
        public ItemStack getResultItem(RegistryAccess registryAccess) {
            return output;
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return SERIALIZER;
        }

        @Override
        public RecipeType<?> getType() {
            return Type.INSTANCE;
        }

        public List<IngredientEntry> getInputs() {
            return this.inputs;
        }

        public static class Type implements RecipeType<BattleRecipe> {
            public static final Type INSTANCE = new Type();
            public Type() {}
        }

        public static class Deserializer implements JsonDeserializer<BattleRecipe> {
            @Override
            public BattleRecipe deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                JsonObject obj = json.getAsJsonObject();
                ResourceLocation typeId = new ResourceLocation(obj.get("type").getAsString());
                if (!typeId.getNamespace().equals("battleofgods")) return null;

                String group = obj.get("group").getAsString();
                String category = obj.has("category") ? obj.get("category").getAsString() : "misc";
                boolean replace = obj.has("replace") && obj.get("replace").getAsBoolean();

                List<IngredientEntry> inputs = new ArrayList<>();
                JsonArray items = obj.getAsJsonArray("items");
                for (JsonElement item : items) {
                    JsonObject entry = item.getAsJsonObject();
                    //int count = entry.get("count").getAsInt();
                    //if (count <= 0) throw new JsonParseException("Invalid count: " + count);

                    if (entry.has("item")) {
                        ItemStack stack = CraftingHelper.getItemStack(entry, true);
                        inputs.add(new IngredientEntry(Ingredient.of(stack), entry.get("count").getAsInt()));
                    } else if (entry.has("tag")) {
                        ResourceLocation tagId = new ResourceLocation(entry.get("tag").getAsString());
                        TagKey<Item> tagKey = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), tagId);
                        inputs.add(new IngredientEntry(Ingredient.of(tagKey), entry.get("count").getAsInt()));
                    }
                }

                JsonObject outputObj = obj.getAsJsonObject("output");
                ItemStack output = CraftingHelper.getItemStack(outputObj, true);
                if (output.getCount() <= 0) throw new JsonParseException("Invalid output count!");

                return new BattleRecipe(typeId, group, category, replace, inputs, output);
            }
        }

        public static class Serializer implements RecipeSerializer<BattleRecipe> {
            @Override
            public BattleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
                // Verwende den Deserializer, um das Rezept aus JSON zu laden
                BattleRecipe recipe = new Deserializer().deserialize(json, null, null);
                if (recipe != null) {
                    return new BattleRecipe(recipeId, recipe.group, recipe.category, recipe.replace, recipe.inputs, recipe.output);
                }
                return null;
            }

            @Override
            public BattleRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
                // Lese die Daten aus dem Netzwerk-Puffer
                String group = buffer.readUtf();
                String category = buffer.readUtf();
                boolean replace = buffer.readBoolean();

                // Lese die Zutaten
                int inputSize = buffer.readVarInt();
                List<IngredientEntry> inputs = new ArrayList<>();
                for (int i = 0; i < inputSize; i++) {
                    Ingredient ingredient = Ingredient.fromNetwork(buffer);
                    int count = buffer.readVarInt();
                    inputs.add(new IngredientEntry(ingredient, count));
                }

                // Lese das Ausgabe-Item
                ItemStack output = buffer.readItem();
                return new BattleRecipe(recipeId, group, category, replace, inputs, output);
            }

            @Override
            public void toNetwork(FriendlyByteBuf buffer, BattleRecipe recipe) {
                // Schreibe die Daten in den Netzwerk-Puffer
                buffer.writeUtf(recipe.group);
                buffer.writeUtf(recipe.category);
                buffer.writeBoolean(recipe.replace);

                // Schreibe die Zutaten
                buffer.writeVarInt(recipe.inputs.size());
                for (IngredientEntry entry : recipe.inputs) {
                    entry.ingredient().toNetwork(buffer);
                    buffer.writeVarInt(entry.count());
                }

                // Schreibe das Ausgabe-Item
                buffer.writeItem(recipe.output);
            }
        }

        public record IngredientEntry(Ingredient ingredient, int count) {}


    }
}