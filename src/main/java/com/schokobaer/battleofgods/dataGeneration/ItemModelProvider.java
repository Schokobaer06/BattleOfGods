package com.schokobaer.battleofgods.dataGeneration;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.subClass.TerrariaArmor;
import com.schokobaer.battleofgods.init.InitItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private int generatedModels = 0;
    private int animatedModels = 0;

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BattleOfGods.MODID, existingFileHelper);
        LOGGER.info("Initializing ItemModelProvider for {}", BattleOfGods.MODID);
    }

    @Override
    protected void registerModels() {
        LOGGER.info("Starting model generation for {} items...", InitItem.ITEMS.getEntries().size());

        for (RegistryObject<Item> entry : InitItem.ITEMS.getEntries()) {
            try {
                Item item = entry.get();
                LOGGER.debug("Processing item: {}", entry.getId());

                if (item instanceof TerrariaArmor armor) {
                    generateArmorModel(armor, entry);
                }
            } catch (Exception e) {
                LOGGER.error("Failed to generate model for {}: {}", entry.getId(), e.getMessage());
            }
        }

        LOGGER.info("Model generation completed. Generated {} models ({} animated)",
                generatedModels, animatedModels);
    }

    private void generateArmorModel(TerrariaArmor armor, RegistryObject<Item> entry) {
        String armorType = getArmorType(armor);
        String materialName = getMaterialName(entry);
        String path = entry.getId().getPath();

        LOGGER.debug("Generating armor model for {} (Type: {}, Material: {})",
                path, armorType, materialName);

        // Prüfe auf Animationen
        boolean isAnimated = isAnimatedArmor(armor);
        if (isAnimated) {
            animatedModels++;
            LOGGER.debug("Item {} is animated, using GeoModel loader", path);
            generateGeckoLibModel(entry, path);
        } else {
            ItemModelBuilder model = withExistingParent(
                    path,
                    new ResourceLocation("item/generated")
            );
            model.texture("layer0", new ResourceLocation(
                    BattleOfGods.MODID,
                    "item/" + materialName + "_" + armorType
            ));
            LOGGER.debug("Generated standard model for {}", path);
        }

        generatedModels++;
    }

    private boolean isAnimatedArmor(TerrariaArmor armor) {
        try {
            // Prüfe auf Geckolib-Animationen
            AnimatableInstanceCache cache = armor.getAnimatableInstanceCache();
            return cache != null && armor.getClass().getMethod("getAnimationResource").getDeclaringClass() != GeoItem.class;
        } catch (Exception e) {
            LOGGER.warn("Animation check failed for {}: {}", armor, e.getMessage());
            return false;
        }
    }

    private void generateGeckoLibModel(RegistryObject<Item> entry, String path) {
        ItemModelBuilder model = getBuilder(path)
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .customLoader((builder, helper) ->
                        new CustomLoaderBuilder<ItemModelBuilder>(
                                new ResourceLocation("geckolib3", "geo"),
                                builder,
                                helper
                        ) {
                            @Override
                            public ItemModelBuilder end() {
                                return builder;
                            }
                        })
                .end();

        LOGGER.debug("Generated GeckoLib model for {}", path);
    }

    private String getArmorType(TerrariaArmor armor) {
        return switch (armor.getType()) {
            case HELMET -> "helmet";
            case CHESTPLATE -> "chestplate";
            case LEGGINGS -> "leggings";
            case BOOTS -> "boots";
        };
    }

    private String getMaterialName(RegistryObject<Item> entry) {
        String registryName = entry.getId().getPath();
        return registryName.replaceFirst("_armor_.*", "");
    }
}