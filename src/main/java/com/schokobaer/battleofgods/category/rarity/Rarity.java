package com.schokobaer.battleofgods.category.rarity;

import com.mojang.datafixers.util.Either;
import com.schokobaer.battleofgods.BattleofgodsMod;
import net.minecraft.resources.ResourceLocation;

/**
 * Class for defining the rarity of an item
 * The rarity is used to determine the color of the item
 * The rarity can be static or animated
 * The rarity can be used to determine the rarity of an item
 */
public class Rarity {
    private final Either<Integer, ResourceLocation> color;
    private final boolean isAnimated;
    private final float animationSpeed;
    private final String displayName;

    /**
     * Rarity with static color
     *
     * @param displayName Name which will be displayed in game
     * @param hexColor    RGB-Color of the rarity
     */
    public Rarity(String displayName, Integer hexColor) {
        if (hexColor == null) {
            throw new IllegalArgumentException("Texture cannot be null");
        }
        this.color = Either.left(hexColor);
        this.isAnimated = false;
        this.animationSpeed = 0;
        this.displayName = CheckDisplayName(displayName);
    }

    /**
     * Rarity with animated color
     *
     * @param displayName Name which will be displayed in game
     * @param texture     Texture to animate
     *                    Texture should be a gradient
     *                    Texture should be 32x32 for best compatibility, else animationSpeed should be adjusted
     *                    Animation speed is set to default (1)
     */
    public Rarity(String displayName, ResourceLocation texture) {
        if (texture == null) {
            throw new IllegalArgumentException("Texture cannot be null");
        }
        this.color = Either.right(texture);
        this.isAnimated = true;
        this.animationSpeed = 1;
        this.displayName = CheckDisplayName(displayName);
    }
    //Constructor for static Colors

    /**
     * Rarity with animated color
     *
     * @param displayName    Name which will be displayed in game
     * @param texture        Texture to animate
     *                       Texture should be a gradient
     *                       Texture should be 32x32 for best compatibility, else animationSpeed should be adjusted
     * @param animationSpeed Speed of the animation
     *                       animationSpeed should be 0 < animationSpeed <= 2
     */
    public Rarity(String displayName, ResourceLocation texture, float animationSpeed) {
        if (texture == null) {
            throw new IllegalArgumentException("Texture cannot be null");
        }
        this.animationSpeed = CheckSpeed(animationSpeed);
        this.displayName = CheckDisplayName(displayName);
        this.color = Either.right(texture);
        this.isAnimated = true;
    }
    // Constructor for animated Colors with default speed

    public String CheckDisplayName(String name) {
        if (name == null || name.isEmpty()) {
            BattleofgodsMod.LOGGER.error("Error: Rarity name is empty or null\nSetting name to Unknown Rarity");
            return "Unknown Rarity";
        }
        return name;
    }
    //Constructor for animated Colors (customizable speed)

    public float CheckSpeed(float speed) {
        if (speed <= 0.0f || speed > 2.0f) {
            BattleofgodsMod.LOGGER.error("Error: animationSpeed must be between 0 and 100\nSetting animationSpeed to 1");
            return 1;
        }
        return speed;
    }

    /**
     * @return Either the hex code or the ResourceLocation
     */
    public Either<Integer, ResourceLocation> getEitherIntegerResourceLocation() {
        if (this.color == null) {
            BattleofgodsMod.LOGGER.error("Error: color is null in Rarity object");
            return Either.left(0xFF000000); // Standardfarbe zurückgeben, wenn null
        }
        return this.color;
    }

    /**
     * Get the color of the rarity
     *
     * @return Either the static hex color or animated hex color of the given ResourceLocation
     */
    public Integer getColor() {
        return RarityColorHandler.getColor(this);
    }

    public float getAnimationSpeed() {
        return this.animationSpeed;
    }

    public boolean isAnimated() {
        return this.isAnimated;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}