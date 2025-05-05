package com.schokobaer.battleofgods.client.renderer;

import com.schokobaer.battleofgods.armor.tier1.WoodArmor;
import com.schokobaer.battleofgods.armor.tier1.WoodArmorModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WoodArmorRenderer extends GeoArmorRenderer<WoodArmor> {
    public WoodArmorRenderer() {
        super(new WoodArmorModel());
    }

    @Override
    public RenderType getRenderType(WoodArmor animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
