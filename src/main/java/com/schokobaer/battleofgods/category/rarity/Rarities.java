package com.schokobaer.battleofgods.category.rarity;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum Rarities implements IExtensibleEnum {

    GRAY(new com.schokobaer.battleofgods.category.rarity.Rarity("Gray", 0x808080), 1),
    WHITE(new com.schokobaer.battleofgods.category.rarity.Rarity("White", 0xFFFFFF), 10),
    BLUE(new com.schokobaer.battleofgods.category.rarity.Rarity("Blue", 0x9696FF), 15),
    GREEN(new com.schokobaer.battleofgods.category.rarity.Rarity("Green", 0x96FF96), 20),
    ORANGE(new com.schokobaer.battleofgods.category.rarity.Rarity("Orange", 0xFFC896), 25),
    LIGHT_RED(new com.schokobaer.battleofgods.category.rarity.Rarity("Light Red", 0xFF9696), 30),
    PINK(new com.schokobaer.battleofgods.category.rarity.Rarity("Pink", 0xFF96FF), 35),
    LIGHT_PURPLE(new com.schokobaer.battleofgods.category.rarity.Rarity("Light Purple", 0xD2A0FF), 40),
    LIME(new com.schokobaer.battleofgods.category.rarity.Rarity("Lime", 0x96FF0A), 45),
    YELLOW(new com.schokobaer.battleofgods.category.rarity.Rarity("Yellow", 0xFFFF0A), 50),
    CYAN(new com.schokobaer.battleofgods.category.rarity.Rarity("Cyan", 0x05C8FF), 55),
    RED(new com.schokobaer.battleofgods.category.rarity.Rarity("Red", 0xFF2864), 60),
    PURPLE(new com.schokobaer.battleofgods.category.rarity.Rarity("Purple", 0xB428FF), 65),
    TURQUOISE(new com.schokobaer.battleofgods.category.rarity.Rarity("Turquoise", 0x00FFC8), 70),
    PURE_GREEN(new com.schokobaer.battleofgods.category.rarity.Rarity("Pure Green", 0x00FF00), 75),
    DARK_BLUE(new com.schokobaer.battleofgods.category.rarity.Rarity("Dark Blue", 0x2B60DE), 80),
    VIOLET(new com.schokobaer.battleofgods.category.rarity.Rarity("Violet", 0x6C2DC7), 85),
    HOT_PINK(new com.schokobaer.battleofgods.category.rarity.Rarity("Hot Pink", 0xFF00FF), 90),
    CALAMITY_RED(new com.schokobaer.battleofgods.category.rarity.Rarity("Calamity Red", 0xA3191A), 95),
    AMBER(new com.schokobaer.battleofgods.category.rarity.Rarity("Amber", 0xFFAF00), 100),
    DARK_ORANGE(new com.schokobaer.battleofgods.category.rarity.Rarity("Dark Orange", 0xCC4723), 105),
    RAINBOW(new com.schokobaer.battleofgods.category.rarity.Rarity("Revengeance Mode", new ResourceLocation("battleofgods:textures/rarity/rainbow.png"), 0.25f), 110),
    FIERY_RED(new com.schokobaer.battleofgods.category.rarity.Rarity("Death Mode", new ResourceLocation("battleofgods:textures/rarity/fiery_red.png")), 115),
    TEAL(new com.schokobaer.battleofgods.category.rarity.Rarity("Eternity Mode", new ResourceLocation("battleofgods:textures/rarity/teal.png"), 0.5f), 120);

    private static final List<net.minecraft.world.item.Rarity> mcRarities = new ArrayList<>();
    private final com.schokobaer.battleofgods.category.rarity.Rarity rarity;
    private final int enchantmentLevel;
    private net.minecraft.world.item.Rarity mcRarity;

    Rarities(com.schokobaer.battleofgods.category.rarity.Rarity rarity, int enchantmentLevel) {
        this.rarity = rarity;
        this.enchantmentLevel = enchantmentLevel;
    }
/*
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        BattleOfGods.LOGGER.info("Initializing Rarities in onCommonSetup...");
        for (Rarities entry : values()) {
            entry.mcRarity = net.minecraft.world.item.Rarity.create(
                    entry.rarity.getDisplayName(),
                    style -> Style.EMPTY.withColor(TextColor.fromRgb(entry.rarity.getColor()))
            );
            mcRarities.add(entry.mcRarity);
            BattleOfGods.LOGGER.info("Initialized Rarity: {} with mcRarity {} and Style {}", entry.name(), entry.mcRarity, entry.mcRarity.getStyleModifier());
        }
        BattleOfGods.LOGGER.info("Registered Rarities: {}", mcRarities);

    }*/

    static {
        BattleOfGods.LOGGER.info("Initializing Rarities in static block...");
        for (Rarities entry : values()) {
            entry.mcRarity = net.minecraft.world.item.Rarity.create(
                    entry.rarity.getDisplayName(),
                    style -> Style.EMPTY.withColor(TextColor.fromRgb(entry.rarity.getColor()))
            );
            mcRarities.add(entry.mcRarity);
            BattleOfGods.LOGGER.info("Initialized Rarity: {} with mcRarity {} and Style {}", entry.name(), entry.mcRarity, entry.mcRarity.getStyleModifier());
        }
        BattleOfGods.LOGGER.info("Registered Rarities: {}", mcRarities);
    }

    public static Rarities create(String name, com.schokobaer.battleofgods.category.rarity.Rarity rarity, int enchantmentLevel) {
        throw new IllegalStateException("Enum not extended");
    }

    public static Rarities shiftRarity(Rarities current, int offset) {
        int newOrdinal = current.ordinal() + offset;
        return Arrays.stream(values())
                .filter(r -> r.ordinal() == newOrdinal)
                .findFirst()
                .orElse(null); // Return null if no matching rarity is found
    }

    public static List<net.minecraft.world.item.Rarity> getMinecraftRarities() {
        return mcRarities;
    }

    public com.schokobaer.battleofgods.category.rarity.Rarity getRarity() {
        return rarity;
    }

    public int getEnchantmentLevel() {
        return enchantmentLevel;
    }

    public net.minecraft.world.item.Rarity asMinecraftRarity() {
        if (this.mcRarity == null) BattleOfGods.LOGGER.warn("Rarity {} is not initialized properly!", this.name());
        return this.mcRarity != null ? this.mcRarity : Rarities.WHITE.mcRarity;
    }
}