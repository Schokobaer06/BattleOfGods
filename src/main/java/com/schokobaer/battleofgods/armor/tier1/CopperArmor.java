package com.schokobaer.battleofgods.armor.tier1;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.subClass.TerrariaArmor;
import com.schokobaer.battleofgods.category.tier.GameTiers;
import com.schokobaer.battleofgods.category.tier.Tiers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

public class CopperArmor extends TerrariaArmor {
    public static final String name = "copper_armor";

    public CopperArmor(Type type) {
        super(name, new int[]{1, 2, 1, 0},
                SoundEvents.ARMOR_EQUIP_GENERIC, type, Tiers.WHITE, GameTiers.TIER_1);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    try {
                        this.renderer = new CopperArmorRenderer();
                    } catch (Exception e) {
                        throw new IllegalStateException("Failed to initialize Renderer", e);
                    }
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    public static class CopperArmorModel extends GeoModel<CopperArmor> {
        @Override
        public ResourceLocation getAnimationResource(CopperArmor object) {
            return new ResourceLocation(BattleOfGods.MODID, "animations/" + name + ".animation.json");
        }

        @Override
        public ResourceLocation getModelResource(CopperArmor object) {
            return new ResourceLocation(BattleOfGods.MODID, "geo/" + name + ".geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(CopperArmor object) {
            return new ResourceLocation(BattleOfGods.MODID, "textures/armor/" + name + ".png");
        }
    }

    public static class CopperArmorRenderer extends GeoArmorRenderer<CopperArmor> {
        public CopperArmorRenderer() {
            super(new CopperArmorModel());
        }

        @Override
        public RenderType getRenderType(CopperArmor animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
            return RenderType.entityTranslucentCull(getTextureLocation(animatable));
        }
    }
}
