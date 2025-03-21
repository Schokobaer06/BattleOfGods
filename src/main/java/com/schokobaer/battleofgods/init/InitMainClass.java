package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.item.ClassTags;
import com.schokobaer.battleofgods.mechanics.item.MainClass;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitMainClass {
    public static final ResourceKey<Registry<MainClass>> MAIN_CLASS_KEY = ResourceKey.createRegistryKey(new ResourceLocation(BattleofgodsMod.MODID, "main_classes"));
    public static final DeferredRegister<MainClass> MAIN_CLASSES = DeferredRegister.create(MAIN_CLASS_KEY, BattleofgodsMod.MODID);

    public static RegistryObject<MainClass> registerItemClass(String name) {
        TagKey<MainClass> tag = ClassTags.createMainClassTag(name);
        return MAIN_CLASSES.register(name, () -> new MainClass(name, tag));
    }
}
