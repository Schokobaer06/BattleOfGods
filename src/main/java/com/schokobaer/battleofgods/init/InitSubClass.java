package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.item.AbstractSubClass;
import com.schokobaer.battleofgods.mechanics.item.SubClassMethods;
import com.schokobaer.battleofgods.mechanics.tag.TagCreator;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class InitSubClass {
    public static final DeferredRegister<Item> SUBCLASSES= DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);
    public static final RegistryObject<Item> BROADSWORD = SUBCLASSES.register("broadsword",
            () -> new Broadsword(TagCreator.createSubClassTag("broadsword", InitMainClass.MELEE)));


}
