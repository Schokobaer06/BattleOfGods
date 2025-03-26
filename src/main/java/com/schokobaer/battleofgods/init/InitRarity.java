package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import com.schokobaer.battleofgods.mechanics.tag.TagCreator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitRarity {
    public static final ResourceKey<Registry<Rarity>> RARITY_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(BattleofgodsMod.MODID, "rarities"));
    public static final DeferredRegister<Rarity> RARITIES =
            DeferredRegister.create(RARITY_KEY, BattleofgodsMod.MODID);

    // Normal Rarities
    public static final RegistryObject<Rarity> GRAY = RARITIES.register("gray",
            () -> new Rarity("Gray", 0x808080, TagCreator.createRarityTag("gray")));

    public static final RegistryObject<Rarity> WHITE = RARITIES.register("white",
            () -> new Rarity("White", 0xFFFFFF, TagCreator.createRarityTag("white")));

    public static final RegistryObject<Rarity> BLUE = RARITIES.register("blue",
            () -> new Rarity("Blue", 0x9696FF, TagCreator.createRarityTag("blue")));

    public static final RegistryObject<Rarity> GREEN = RARITIES.register("green",
            () -> new Rarity("Green", 0x96FF96, TagCreator.createRarityTag("green")));

    public static final RegistryObject<Rarity> ORANGE = RARITIES.register("orange",
            () -> new Rarity("Orange", 0xFFC896, TagCreator.createRarityTag("orange")));

    public static final RegistryObject<Rarity> LIGHT_RED = RARITIES.register("light_red",
            () -> new Rarity("Light Red", 0xFF9696, TagCreator.createRarityTag("light_red")));

    public static final RegistryObject<Rarity> PINK = RARITIES.register("pink",
            () -> new Rarity("Pink", 0xFF96FF, TagCreator.createRarityTag("pink")));

    public static final RegistryObject<Rarity> LIGHT_PURPLE = RARITIES.register("light_purple",
            () -> new Rarity("Light Purple", 0xD2A0FF, TagCreator.createRarityTag("light_purple")));

    public static final RegistryObject<Rarity> LIME = RARITIES.register("lime",
            () -> new Rarity("Lime", 0x96FF0A, TagCreator.createRarityTag("lime")));

    public static final RegistryObject<Rarity> YELLOW = RARITIES.register("yellow",
            () -> new Rarity("Yellow", 0xFFFF0A, TagCreator.createRarityTag("yellow")));

    public static final RegistryObject<Rarity> CYAN = RARITIES.register("cyan",
            () -> new Rarity("Cyan", 0x05C8FF, TagCreator.createRarityTag("cyan")));

    public static final RegistryObject<Rarity> RED = RARITIES.register("red",
            () -> new Rarity("Red", 0xFF2864, TagCreator.createRarityTag("red")));

    public static final RegistryObject<Rarity> PURPLE = RARITIES.register("purple",
            () -> new Rarity("Purple", 0xB428FF, TagCreator.createRarityTag("purple")));

    public static final RegistryObject<Rarity> TURQUOISE = RARITIES.register("turquoise",
            () -> new Rarity("Turquoise", 0x00FFC8, TagCreator.createRarityTag("turquoise")));

    public static final RegistryObject<Rarity> PURE_GREEN = RARITIES.register("pure_green",
            () -> new Rarity("Pure Green", 0x00FF00, TagCreator.createRarityTag("pure_green")));

    public static final RegistryObject<Rarity> DARK_BLUE = RARITIES.register("dark_blue",
            () -> new Rarity("Dark Blue", 0x2B60DE, TagCreator.createRarityTag("dark_blue")));

    public static final RegistryObject<Rarity> VIOLET = RARITIES.register("violet",
            () -> new Rarity("Violet", 0x6C2DC7, TagCreator.createRarityTag("violet")));

    public static final RegistryObject<Rarity> HOT_PINK = RARITIES.register("hot_pink",
            () -> new Rarity("Hot Pink", 0xFF00FF, TagCreator.createRarityTag("hot_pink")));

    public static final RegistryObject<Rarity> CALAMITY_RED = RARITIES.register("calamity_red",
            () -> new Rarity("Calamity Red", 0xA3191A, TagCreator.createRarityTag("calamity_red")));

    // Special Rarities
    public static final RegistryObject<Rarity> AMBER = RARITIES.register("amber",
            () -> new Rarity("Quest", 0xFFAF00, TagCreator.createRarityTag("amber")));

    public static final RegistryObject<Rarity> DARK_ORANGE = RARITIES.register("dark_orange",
            () -> new Rarity("Draedon's Arsenal", 0xCC4723, TagCreator.createRarityTag("dark_orange")));

    // Animated Rarities
    public static final RegistryObject<Rarity> RAINBOW = RARITIES.register("rainbow",
            () -> new Rarity("Revengeance Mode",
                    new ResourceLocation("battleofgods:textures/rarity/rainbow.png"),
                    0.25f, TagCreator.createRarityTag("rainbow")));

    public static final RegistryObject<Rarity> FIERY_RED = RARITIES.register("fiery_red",
            () -> new Rarity("Death Mode",
                    new ResourceLocation("battleofgods:textures/rarity/fiery_red.png"),
                    TagCreator.createRarityTag("fiery_red")));

    public static final RegistryObject<Rarity> TEAL = RARITIES.register("teal",
            () -> new Rarity("Eternity Mode",
                    new ResourceLocation("battleofgods:textures/rarity/teal.png"),
                    0.5f, TagCreator.createRarityTag("teal")));
}