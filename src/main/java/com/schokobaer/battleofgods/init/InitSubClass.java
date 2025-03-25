package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.item.override.ItemOverride;
import com.schokobaer.battleofgods.mechanics.tag.TagCreator;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.*;

import java.util.Objects;

public class InitSubClass {

    public static final ResourceKey<? extends Registry<ItemOverride>> ITEM_OVERRIDE = ResourceKey.createRegistryKey(new ResourceLocation(BattleofgodsMod.MODID, "items"));

    public static final DeferredRegister<Item> SUBCLASSES= DeferredRegister.create(ForgeRegistries.ITEMS, BattleofgodsMod.MODID);
    public static final RegistryObject<ItemOverride> BROADSWORD = SUBCLASSES.register("broadsword",
            () -> new Broadsword(TagCreator.createSubClassTag("broadsword", InitMainClass.MELEE)));


}
