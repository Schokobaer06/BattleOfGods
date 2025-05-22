package com.schokobaer.battleofgods.category.rarity;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.UnaryOperator;

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

    public Rarity getRarity() {
        return rarity;
    }

    /**
     * Returns the rarity from the offset of the given rarity
     * @param current The rarity from which the offset rarity is returned
     * @param offset The offset to the current rarity
     * @return The rarity from the offset of the given rarity. Returns null if no rarity is found at offset
     */
    public static Rarities shiftRarity(Rarities current, int offset) {
        int newOrdinal = current.ordinal() + offset;
        return Arrays.stream(values())
                .filter(r -> r.ordinal() == newOrdinal)
                .findFirst()
                .orElse(null); // Gibt null zurück, wenn der neue Ordinalwert ungültig ist
    }


    /**
     *  @return The rarity as a net.minecraft.world.item.Rarity
     */
    public net.minecraft.world.item.Rarity asMinecraftRarity(){
        try {
            net.minecraft.world.item.Rarity minecraftRarity = net.minecraft.world.item.Rarity.create(this.rarity.getDisplayName(), style -> {
                style.withColor(this.rarity.getColor());
                return style;
            });
            ;
            return minecraftRarity;
        } catch (Exception e){
            BattleOfGods.LOGGER.warn("Error: failed to create net.minecraft.world.item.Rarity for {} with color {}:{}", this.rarity.getDisplayName(), this.rarity.getColor(), e.getMessage());

            return switch (this) {
                case LIGHT_RED, PINK, LIGHT_PURPLE -> net.minecraft.world.item.Rarity.UNCOMMON;
                case LIME, YELLOW, CYAN, RED, PURPLE -> net.minecraft.world.item.Rarity.RARE;
                case TURQUOISE, PURE_GREEN, DARK_BLUE, VIOLET, HOT_PINK, CALAMITY_RED, AMBER, DARK_ORANGE, RAINBOW, FIERY_RED, TEAL -> net.minecraft.world.item.Rarity.EPIC;
                default -> net.minecraft.world.item.Rarity.COMMON;
            };
        }
    }

    /**
     * Converts a net.minecraft.world.item.Rarity to a Rarity
     * @param rarity The net.minecraft.world.item.Rarity to convert
     * @return The converted Rarity
     */
    public static Rarity asRarity(net.minecraft.world.item.Rarity rarity) {
        var name = rarity.name();
        int style = Objects.requireNonNull(rarity.getStyleModifier().apply(Style.EMPTY).getColor()).getValue();
        return new Rarity(name, style);
    }

}