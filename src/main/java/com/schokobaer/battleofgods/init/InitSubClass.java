package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.override.ItemOverride;
import com.schokobaer.battleofgods.subClass.Broadsword;
import com.schokobaer.battleofgods.subClass.Shortsword;
import com.schokobaer.battleofgods.tag.TagCreator;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitSubClass {

    //public static final ResourceKey<? extends Registry<ItemOverride>> ITEM_OVERRIDE = ResourceKey.createRegistryKey(new ResourceLocation(BattleofgodsMod.MODID, "items"));

    public static final DeferredRegister<Item> SUBCLASSES= DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);

    public static final RegistryObject<ItemOverride> BROADSWORD = SUBCLASSES.register("broadsword",
            () -> new Broadsword(TagCreator.createSubClassTag("broadsword", InitMainClass.MELEE)));
    public static final RegistryObject<ItemOverride> SHORTSWORD = SUBCLASSES.register("shortsword",
            () -> new Shortsword(TagCreator.createSubClassTag("shortsword", InitMainClass.MELEE)));


}
