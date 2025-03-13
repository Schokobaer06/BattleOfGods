package com.schokobaer.battleofgods.mechanics.rarity;

import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceLocation;

public class Rarity {
    private final Either<Integer, ResourceLocation> color;
    private final boolean isAnimated;
    private final float animationSpeed;

    // Constructor for hex strings
    public Rarity(String hexColor) {
        this.color = Either.left(ColorUtils.hexToInt(hexColor));
        this.isAnimated = false;
        this.animationSpeed = 0;
    }
    //Constructor for static Colors
    public Rarity(int hexColor){
        this.color = Either.left(hexColor);
        this.isAnimated = false;
        this.animationSpeed = 0;
    }
    // Constructor for animated Colors (default speed) is 1

    /**
     * Animated Rarity
     * @param texture Texture to animate
     *                Texture should be a gradient
     *                Animation speed is default (1)
     */
    public Rarity(ResourceLocation texture){
        this.color = Either.right(texture);
        this.isAnimated = true;
        this.animationSpeed = 1;
    }
    //Constructor for animated Colors (customizable speed)

    /**
     * Animated Rarity
     * @param texture Texture to animate
     *                Texture should be a gradient
     * @param animationSpeed Speed of the animation
     *
     */
    public Rarity (ResourceLocation texture, float animationSpeed){
        this.color = Either.right(texture);
        this.isAnimated = true;
        this.animationSpeed = animationSpeed;
    }

    /**
     * Get the color of the rarity
     * @return Either the hex color or the ResourceLocation
     */
    public Either<Integer, ResourceLocation> getColor(){
        return this.color;
    }

    /**
     * Get the color of the rarity
     * @return the ARGB-Color of the rarity for display
     */
    public int getArgbColor(){
        return RarityColorHandler.getColor(this);
    }

    public float getAnimationSpeed() {
        return this.animationSpeed;
    }
    public boolean isAnimated(){
        return this.isAnimated;
    }
}
