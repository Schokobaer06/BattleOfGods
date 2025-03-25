package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
//import com.schokobaer.battleofgods.mechanics.item.override.Item;
import com.schokobaer.battleofgods.mechanics.tag.TagCreator;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitSubClass {
    //public static final ResourceKey<Registry<Item>> ITEM_KEY = ResourceKey.createRegistryKey(new ResourceLocation(BattleofgodsMod.MODID, "items"));
    public static final DeferredRegister<Item> SUBCLASSES= DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);

    public static final RegistryObject<Item> BROADSWORD = SUBCLASSES.register("broadsword",
            () -> new Broadsword(TagCreator.createSubClassTag("broadsword", null)));


}
