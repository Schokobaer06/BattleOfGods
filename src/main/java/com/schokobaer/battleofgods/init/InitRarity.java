package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitRarity {
    public static final ResourceKey<Registry<Rarity>> RARITY_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(BattleOfGods.MODID, "rarities"));
    public static final DeferredRegister<Rarity> RARITIES =
            DeferredRegister.create(RARITY_KEY, BattleOfGods.MODID);

    // Normal Rarities
    public static final RegistryObject<Rarity> GRAY = RARITIES.register("gray",
            () -> new Rarity("Gray", 0x808080));

    public static final RegistryObject<Rarity> WHITE = RARITIES.register("white",
            () -> new Rarity("White", 0xFFFFFF));

    public static final RegistryObject<Rarity> BLUE = RARITIES.register("blue",
            () -> new Rarity("Blue", 0x9696FF));

    public static final RegistryObject<Rarity> GREEN = RARITIES.register("green",
            () -> new Rarity("Green", 0x96FF96));

    public static final RegistryObject<Rarity> ORANGE = RARITIES.register("orange",
            () -> new Rarity("Orange", 0xFFC896));

    public static final RegistryObject<Rarity> LIGHT_RED = RARITIES.register("light_red",
            () -> new Rarity("Light Red", 0xFF9696));

    public static final RegistryObject<Rarity> PINK = RARITIES.register("pink",
            () -> new Rarity("Pink", 0xFF96FF));

    public static final RegistryObject<Rarity> LIGHT_PURPLE = RARITIES.register("light_purple",
            () -> new Rarity("Light Purple", 0xD2A0FF));

    public static final RegistryObject<Rarity> LIME = RARITIES.register("lime",
            () -> new Rarity("Lime", 0x96FF0A));

    public static final RegistryObject<Rarity> YELLOW = RARITIES.register("yellow",
            () -> new Rarity("Yellow", 0xFFFF0A));

    public static final RegistryObject<Rarity> CYAN = RARITIES.register("cyan",
            () -> new Rarity("Cyan", 0x05C8FF));

    public static final RegistryObject<Rarity> RED = RARITIES.register("red",
            () -> new Rarity("Red", 0xFF2864));

    public static final RegistryObject<Rarity> PURPLE = RARITIES.register("purple",
            () -> new Rarity("Purple", 0xB428FF));

    public static final RegistryObject<Rarity> TURQUOISE = RARITIES.register("turquoise",
            () -> new Rarity("Turquoise", 0x00FFC8));

    public static final RegistryObject<Rarity> PURE_GREEN = RARITIES.register("pure_green",
            () -> new Rarity("Pure Green", 0x00FF00));

    public static final RegistryObject<Rarity> DARK_BLUE = RARITIES.register("dark_blue",
            () -> new Rarity("Dark Blue", 0x2B60DE));

    public static final RegistryObject<Rarity> VIOLET = RARITIES.register("violet",
            () -> new Rarity("Violet", 0x6C2DC7));

    public static final RegistryObject<Rarity> HOT_PINK = RARITIES.register("hot_pink",
            () -> new Rarity("Hot Pink", 0xFF00FF));

    public static final RegistryObject<Rarity> CALAMITY_RED = RARITIES.register("calamity_red",
            () -> new Rarity("Calamity Red", 0xA3191A));

    // Special Rarities
    public static final RegistryObject<Rarity> AMBER = RARITIES.register("amber",
            () -> new Rarity("Quest", 0xFFAF00));

    public static final RegistryObject<Rarity> DARK_ORANGE = RARITIES.register("dark_orange",
            () -> new Rarity("Draedon's Arsenal", 0xCC4723));

    // Animated Rarities
    public static final RegistryObject<Rarity> RAINBOW = RARITIES.register("rainbow",
            () -> new Rarity("Revengeance Mode",
                    new ResourceLocation("battleofgods:textures/rarity/rainbow.png"),
                    0.25f));

    public static final RegistryObject<Rarity> FIERY_RED = RARITIES.register("fiery_red",
            () -> new Rarity("Death Mode",
                    new ResourceLocation("battleofgods:textures/rarity/fiery_red.png")));

    public static final RegistryObject<Rarity> TEAL = RARITIES.register("teal",
            () -> new Rarity("Eternity Mode",
                    new ResourceLocation("battleofgods:textures/rarity/teal.png"),
                    0.5f));
}