package com.schokobaer.battleofgods.item.model;

import com.schokobaer.battleofgods.item.WoodArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WoodArmorModel extends GeoModel<WoodArmorItem> {
    @Override
    public ResourceLocation getAnimationResource(WoodArmorItem object) {
        return new ResourceLocation("battleofgods", "animations/wooden_armor.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(WoodArmorItem object) {
        return new ResourceLocation("battleofgods", "geo/wooden_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WoodArmorItem object) {
        return new ResourceLocation("battleofgods", "textures/item/wood_armor.png");
    }
}
