package com.schokobaer.battleofgods.armor.tier1;

import com.schokobaer.battleofgods.armor.TerrariaArmorItem;
import com.schokobaer.battleofgods.client.renderer.WoodArmorRenderer;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

public class WoodArmor extends TerrariaArmorItem {

    public WoodArmor(Type type, Properties properties) {
        super(new int[]{0, 1, 1, 0},
                "wood_armor",
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic")),
                15, type, properties,
                InitRarity.WHITE, InitTier.TIER_1);
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
}
