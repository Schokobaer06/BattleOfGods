package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleOfGods;
import com.schokobaer.battleofgods.category.mainClass.MainClass;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitMainClass {
    //TODO
    public static final ResourceKey<Registry<MainClass>> MAIN_CLASS_KEY = ResourceKey.createRegistryKey(new ResourceLocation(BattleOfGods.MODID, "main_classes"));
    public static final DeferredRegister<MainClass> MAIN_CLASSES = DeferredRegister.create(MAIN_CLASS_KEY, BattleOfGods.MODID);

    public static final RegistryObject<MainClass> MISC = registerMainClass("misc");
    public static final RegistryObject<MainClass> MELEE = registerMainClass("melee");
    public static final RegistryObject<MainClass> RANGED = registerMainClass("ranged");
    public static final RegistryObject<MainClass> MAGIC = registerMainClass("magic");
    public static final RegistryObject<MainClass> SUMMONING = registerMainClass("summoning");

    public static final RegistryObject<MainClass> ROGUE = registerMainClass("rogue");
    public static final RegistryObject<MainClass> HEALING = registerMainClass("healing");
    public static final RegistryObject<MainClass> BARD = registerMainClass("bard");
    public static final RegistryObject<MainClass> TOOL = registerMainClass("tool");

    public static final RegistryObject<MainClass> ARMOR = registerMainClass("armor");

    public static RegistryObject<MainClass> registerMainClass(String name) {
        return MAIN_CLASSES.register(name, () -> new MainClass(name));
    }
}
