package com.schokobaer.battleofgods.armor.tier1;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WoodArmorModel extends GeoModel<WoodArmor> {
    @Override
    public ResourceLocation getAnimationResource(WoodArmor object) {
        return new ResourceLocation("battleofgods", "animations/wooden_armor.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(WoodArmor object) {
        return new ResourceLocation("battleofgods", "geo/wooden_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WoodArmor object) {
        return new ResourceLocation("battleofgods", "textures/armor/wood_armor.png");
    }
}
