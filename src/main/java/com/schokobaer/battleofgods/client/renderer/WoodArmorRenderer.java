package com.schokobaer.battleofgods.client.renderer;

import com.schokobaer.battleofgods.armor.tier1.WoodArmor;
import com.schokobaer.battleofgods.armor.tier1.WoodArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WoodArmorRenderer extends GeoArmorRenderer<WoodArmor> {
    public WoodArmorRenderer() {
        super(new WoodArmorModel());
    }
/*
    @Override
    public RenderType getRenderType(WoodArmorItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }*/
}
