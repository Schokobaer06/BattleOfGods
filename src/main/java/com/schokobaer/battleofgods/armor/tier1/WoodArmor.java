package com.schokobaer.battleofgods.armor.tier1;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.rarity.Rarities;
import com.schokobaer.battleofgods.category.subClass.TerrariaArmor;
import com.schokobaer.battleofgods.category.tier.GameTiers;
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

public class WoodArmor extends TerrariaArmor {
    public static final String name = "wood_armor";

    public WoodArmor(Type type) {
        super(name, new int[]{0, 1, 1, 0},
                SoundEvents.ARMOR_EQUIP_LEATHER, type, Rarities.WHITE, GameTiers.TIER_1);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    try {
                        this.renderer = new WoodArmorRenderer();
                    } catch (Exception e) {
                        throw new IllegalStateException("Failed to initialize WoodArmorRenderer", e);
                    }
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    public static class WoodArmorModel extends GeoModel<WoodArmor> {
        @Override
        public ResourceLocation getAnimationResource(WoodArmor object) {
            return new ResourceLocation(BattleOfGods.MODID, "animations/wood_armor.animation.json");
        }

        @Override
        public ResourceLocation getModelResource(WoodArmor object) {
            return new ResourceLocation(BattleOfGods.MODID, "geo/wood_armor.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(WoodArmor object) {
            return new ResourceLocation(BattleOfGods.MODID, "textures/armor/wood_armor.png");
        }
    }

    public static class WoodArmorRenderer extends GeoArmorRenderer<WoodArmor> {
        public WoodArmorRenderer() {
            super(new WoodArmorModel());
        }

        @Override
        public RenderType getRenderType(WoodArmor animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
            return RenderType.entityTranslucentCull(getTextureLocation(animatable));
        }
    }
}
