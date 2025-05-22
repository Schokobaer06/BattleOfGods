package com.schokobaer.battleofgods.category.rarity;

import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

public enum Rarities {
    GRAY(new Rarity("Gray", 0x808080)),
    WHITE(new Rarity("White", 0xFFFFFF)),
    BLUE(new Rarity("Blue", 0x9696FF)),
    GREEN(new Rarity("Green", 0x96FF96)),
    ORANGE(new Rarity("Orange", 0xFFC896)),
    LIGHT_RED(new Rarity("Light Red", 0xFF9696)),
    PINK(new Rarity("Pink", 0xFF96FF)),
    LIGHT_PURPLE(new Rarity("Light Purple", 0xD2A0FF)),
    LIME(new Rarity("Lime", 0x96FF0A)),
    YELLOW(new Rarity("Yellow", 0xFFFF0A)),
    CYAN(new Rarity("Cyan", 0x05C8FF)),
    RED(new Rarity("Red", 0xFF2864)),
    PURPLE(new Rarity("Purple", 0xB428FF)),
    TURQUOISE(new Rarity("Turquoise", 0x00FFC8)),
    PURE_GREEN(new Rarity("Pure Green", 0x00FF00)),
    DARK_BLUE(new Rarity("Dark Blue", 0x2B60DE)),
    VIOLET(new Rarity("Violet", 0x6C2DC7)),
    HOT_PINK(new Rarity("Hot Pink", 0xFF00FF)),
    CALAMITY_RED(new Rarity("Calamity Red", 0xA3191A)),
    AMBER(new Rarity("Quest", 0xFFAF00)),
    DARK_ORANGE(new Rarity("Draedon's Arsenal", 0xCC4723)),
    RAINBOW(new Rarity("Revengeance Mode", new ResourceLocation("battleofgods:textures/rarity/rainbow.png"), 0.25f)),
    FIERY_RED(new Rarity("Death Mode", new ResourceLocation("battleofgods:textures/rarity/fiery_red.png"))),
    TEAL(new Rarity("Eternity Mode", new ResourceLocation("battleofgods:textures/rarity/teal.png"), 0.5f));

    private final Rarity rarity;

    Rarities(Rarity rarity) {
        this.rarity = rarity;
    }

    /**
     * Returns the rarity from the offset of the given rarity
     *
     * @param current The rarity from which the offset rarity is returned
     * @param offset  The offset to the current rarity
     * @return The rarity from the offset of the given rarity. Returns null if no rarity is found at offset
     */
    public static Rarities shiftRarity(Rarities current, int offset) {
        int newOrdinal = current.ordinal() + offset;
        return Arrays.stream(values())
                .filter(r -> r.ordinal() == newOrdinal)
                .findFirst()
                .orElse(null); // Gibt null zurück, wenn der neue Ordinalwert ungültig ist
    }

    public Rarity getRarity() {
        return rarity;
    }


}