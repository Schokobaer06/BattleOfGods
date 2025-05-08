package com.schokobaer.battleofgods.handler;

import com.google.gson.*;
import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeHandler {
    private static final List<BattleRecipe> RECIPES = new ArrayList<>();
    private static final Map<ResourceLocation, BattleRecipe> RECIPE_MAP = new HashMap<>();

    // Comparator, sorts by category and then by group
    private static final Comparator<BattleRecipe> RECIPE_COMPARATOR =
            Comparator.comparing(BattleRecipe::getCategory)
                    .thenComparing(BattleRecipe::getGroup);

    public static void loadRecipes(ResourceManager resourceManager) {
        RECIPE_MAP.clear();
        RECIPES.clear();
        try {

            Set<ResourceLocation> resources = getResources(resourceManager);

            BattleOfGods.LOGGER.debug("Found {} recipe resources", resources.size());

            for (ResourceLocation resource : resources) {
                try (InputStream stream = resourceManager.getResource(resource).get().open()) {
                    // Extrahiere die Rezept-ID aus dem Dateipfad
                    String path = resource.getPath(); // z.B. 'recipes/copper_broadsword.json'
                    String recipeName = path.substring("recipes/".length(), path.length() - ".json".length());
                    ResourceLocation recipeId = new ResourceLocation(BattleOfGods.MODID, recipeName);

                    // Parse die JSON-Datei manuell
                    JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                    String group = json.get("group").getAsString();
                    String category = json.has("category") ? json.get("category").getAsString() : "misc";
                    boolean replace = json.has("replace") && json.get("replace").getAsBoolean();

                    // Parse die Zutaten
                    List<BattleRecipe.IngredientEntry> inputs = new ArrayList<>();
                    JsonArray items = json.getAsJsonArray("items");
                    for (JsonElement item : items) {
                        JsonObject entry = item.getAsJsonObject();
                        if (entry.has("item")) {
                            ItemStack stack = CraftingHelper.getItemStack(entry, true);
                            inputs.add(new BattleRecipe.IngredientEntry(Ingredient.of(stack), entry.get("count").getAsInt()));
                        } else if (entry.has("tag")) {
                            ResourceLocation tagId = new ResourceLocation(entry.get("tag").getAsString());
                            TagKey<Item> tagKey = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), tagId);
                            inputs.add(new BattleRecipe.IngredientEntry(Ingredient.of(tagKey), entry.get("count").getAsInt()));
                        }
                    }

                    // Parse das Ausgabe-Item
                    JsonObject outputObj = json.getAsJsonObject("output");
                    ItemStack output = CraftingHelper.getItemStack(outputObj, true);

                    // Erstelle das Rezept mit der korrekten ID
                    BattleRecipe recipe = new BattleRecipe(recipeId, group, category, replace, inputs, output);
                    if (recipe.isValid()) {
                        if (RECIPE_MAP.containsKey(recipeId)) {
                            BattleOfGods.LOGGER.warn("Duplicate recipe ID found: {}", recipeId);
                        }
                        if (recipe.isReplace())
                            RECIPES.removeIf(r -> r.getOutput().getItem().equals(recipe.getOutput().getItem()));
                        RECIPES.add(recipe);
                        BattleOfGods.LOGGER.debug("Loaded recipe: {}", recipeId);
                    }
                } catch (Exception e) {
                    if (!(e instanceof NullPointerException))
                        BattleOfGods.LOGGER.error("Failed to load recipe: {}", resource, e);
                    //BattleOfGods.LOGGER.warn("Failed to load recipe: {}", resource);
                }
            }

            RECIPES.forEach(recipe -> RECIPE_MAP.put(recipe.getId(), recipe));
            BattleOfGods.LOGGER.info("Loaded {} recipes", RECIPES.size());
        } catch (Exception e) {
            BattleOfGods.LOGGER.error("Failed to load recipes!", e);
        }
    }

    private static Set<ResourceLocation> getResources(ResourceManager resourceManager) {
        Set<String> namespaces = resourceManager.getNamespaces();
        //BattleOfGods.LOGGER.debug("Available namespaces: {}", namespaces);

        // Suche nach allen JSON-Dateien im 'recipes'-Ordner des Mods
        Set<ResourceLocation> resources = new HashSet<>();
        namespaces.forEach(namespace -> {
            try {
                resources.addAll(resourceManager.listResources(
                        "recipes",
                        rl -> rl.getNamespace().equals(BattleOfGods.MODID) && rl.getPath().endsWith(".json")
                ).keySet());
            } catch (Exception e) {
                BattleOfGods.LOGGER.warn("Error listing resources in namespace: {}", namespace, e);
            }
        });
        return resources;
    }

    /**
     * Get all craftable recipes for the player
     *
     * @param player current player
     * @return List<BattleRecipe> list of craftable recipes
     */
    public static List<BattleRecipe> getCraftableRecipes(Player player) {
        return RECIPES.stream()
                .filter(recipe -> hasRequiredItems(player, recipe))
                .sorted(RECIPE_COMPARATOR)
                .collect(Collectors.toList());
    }

    /**
     * Get all recipes
     *
     * @return List<BattleRecipe> list of all recipes
     */
    public static List<BattleRecipe> getAllRecipes() {
        return RECIPES.stream()
                .sorted(RECIPE_COMPARATOR)
                .collect(Collectors.toList());
    }

    private static boolean hasRequiredItems(Player player, BattleRecipe recipe) {
        for (BattleRecipe.IngredientEntry entry : recipe.getInputs()) {
            int required = entry.count();
            int available = 0;
            for (ItemStack stack : player.getInventory().items) {
                if (entry.ingredient().test(stack)) {
                    available += stack.getCount();
                    //if (available >= required) break;
                }
            }

            if (available < required) return false;
        }
        return true;
    }

    /**
     * Get a recipe by its ID
     *
     * @param recipeId the ID of the recipe
     * @return Optional<BattleRecipe> the recipe if it exists, otherwise Optional.empty()
     */
    public static Optional<BattleRecipe> getRecipeById(ResourceLocation recipeId) {
        return Optional.ofNullable(RECIPE_MAP.get(recipeId));
    }

    /**
     * Get all recipes by their group
     *
     * @param group the group of the recipe
     * @return List<BattleRecipe> list of recipes in the group
     */
    public static List<BattleRecipe> getRecipesByGroup(String group) {
        return RECIPES.stream()
                .filter(r -> r.group.equals(group))
                .sorted(RECIPE_COMPARATOR)
                .collect(Collectors.toList());
    }

    /**
     * Get all craftable recipes by their group
     *
     * @param player current player
     * @param group  the group of the recipe
     * @return
     */
    public static List<BattleRecipe> getCraftableRecipesByGroup(Player player, String group) {
        return getRecipesByGroup(group).stream()
                .filter(r -> hasRequiredItems(player, r))
                .sorted(RECIPE_COMPARATOR)
                .collect(Collectors.toList());
    }

    public static List<RecipeHandler.BattleRecipe> getRecipesByIngredient(Ingredient ingredient) {
        return getAllRecipes().stream()
                .filter(recipe -> recipe.getInputs().stream()
                        .anyMatch(entry -> entry.ingredient().equals(ingredient)))
                .sorted(RECIPE_COMPARATOR)
                .collect(Collectors.toList());
    }


    public static class BattleRecipe implements Recipe<Container> {
        public static final Serializer SERIALIZER = new Serializer();
        private final ResourceLocation id;
        private final String group;
        private final String category;
        private final boolean replace;
        private final List<IngredientEntry> inputs;
        private final ItemStack output;

        /**
         * Constructor for BattleRecipe
         *
         * @param id       the ID of the recipe
         * @param group    the group of the recipe
         * @param category the category of the recipe
         * @param replace  whether to replace the recipe
         * @param inputs   the inputs of the recipe
         * @param output   the output of the recipe
         */
        public BattleRecipe(ResourceLocation id, String group, String category, boolean replace, List<IngredientEntry> inputs, ItemStack output) {
            this.id = id;
            this.group = group;
            this.category = category;
            this.replace = replace;
            this.inputs = inputs;
            this.output = output;
        }

        public boolean isValid() {
            return output != null && !output.isEmpty() && inputs.stream().noneMatch(i -> i.ingredient().isEmpty());
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

        // Getter für Category und Group für die Sortierung
        public String getCategory() {
            return category;
        }

        public String getGroup() {
            return group;
        }

        public ItemStack getOutput() {
            return output;
        }

        public boolean isReplace() {
            return replace;
        }

        public static class Type implements RecipeType<BattleRecipe> {
            public static final Type INSTANCE = new Type();

            public Type() {
            }
        }

        public static class Deserializer implements JsonDeserializer<BattleRecipe> {

            @Override
            public BattleRecipe deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context) throws JsonParseException {
                JsonObject obj = json.getAsJsonObject();
                // Extrahiere die Felder
                ResourceLocation id = new ResourceLocation(obj.get("type").getAsString());
                String group = obj.get("group").getAsString();
                String category = obj.has("category") ? obj.get("category").getAsString() : "misc";
                boolean replace = obj.has("replace") && obj.get("replace").getAsBoolean();

                // Parse Inputs
                List<IngredientEntry> inputs = new ArrayList<>();
                JsonArray items = obj.getAsJsonArray("items");
                for (JsonElement item : items) {
                    JsonObject entry = item.getAsJsonObject();
                    if (entry.has("item")) {
                        ItemStack stack = CraftingHelper.getItemStack(entry, true);
                        inputs.add(new IngredientEntry(Ingredient.of(stack), entry.get("count").getAsInt()));
                    } else if (entry.has("tag")) {
                        ResourceLocation tagId = new ResourceLocation(entry.get("tag").getAsString());
                        TagKey<Item> tagKey = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), tagId);
                        inputs.add(new IngredientEntry(Ingredient.of(tagKey), entry.get("count").getAsInt()));
                    }
                }

                // Parse Output
                JsonObject outputObj = obj.getAsJsonObject("output");
                ItemStack output = CraftingHelper.getItemStack(outputObj, true);

                return new BattleRecipe(id, group, category, replace, inputs, output);
            }
        }

        public static class Serializer implements RecipeSerializer<BattleRecipe> {
            @Override
            public BattleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
                // Verwende den Deserializer, um das Rezept aus JSON zu laden
                BattleRecipe recipe = new Deserializer().deserialize(json, null, null);
                if (recipe != null) {
                    return new BattleRecipe(recipeId,
                            recipe.group,
                            recipe.category,
                            recipe.replace,
                            recipe.inputs,
                            recipe.output);
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

        public record IngredientEntry(Ingredient ingredient, int count) {
        }
    }
}
