package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.item.ItemClass;
import com.schokobaer.battleofgods.mechanics.item.ItemSubClass;
import com.schokobaer.battleofgods.mechanics.item.ItemSubClassTags;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItemSubClass {
    public static final DeferredRegister<ItemSubClass> ITEM_SUBCLASSES = DeferredRegister.create(ForgeRegistries.ITEMS.getRegistryName(), BattleofgodsMod.MODID);

    public static final RegistryObject<ItemSubClass> BROADSWORD = registerBroadSwordSubClass(new Item.Properties(), "broadsword", InitItemClass.MELEE);

    private static RegistryObject<ItemSubClass> registerBroadSwordSubClass(Item.Properties properties, String name, RegistryObject<ItemClass> itemClass) {
        TagKey<Item> tag = ItemSubClassTags.create(name, itemClass);
        return ITEM_SUBCLASSES.register(name, () -> new Broadsword(properties, name, itemClass, tag));
    }
}