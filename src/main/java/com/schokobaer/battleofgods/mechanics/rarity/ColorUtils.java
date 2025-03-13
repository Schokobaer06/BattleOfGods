package com.schokobaer.battleofgods.mechanics.rarity;

public class ColorUtils {
    public static int hexToInt(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1); // Entferne das #
        }

        if (hex.length() == 6) {
            // Füge Alpha-Wert hinzu (vollständig undurchsichtig)
            hex = "FF" + hex;
        } else if (hex.length() != 8) {
            throw new IllegalArgumentException("Invalid hex-Format: " + hex);
        }

        // Konvertiere den Hex-String in einen int
        return (int) Long.parseLong(hex, 16);
    }
}
