package com.schokobaer.battleofgods.item.tier1;

import com.schokobaer.battleofgods.init.InitItemClass;
import com.schokobaer.battleofgods.init.InitItemSubClass;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import com.schokobaer.battleofgods.mechanics.item.ItemSubClass;
import com.schokobaer.battleofgods.mechanics.item.subClass.Broadsword;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;

public class ItemCopperBroadsword extends SwordItem {
    private Broadsword broadsword;
    public ItemCopperBroadsword() {
        super(new Tier() {
            public int getUses() {
                return 0;
            }

            public float getSpeed() {
                return 2f;
            }

            public float getAttackDamageBonus() {
                return 5f;
            }

            public int getLevel() {
                return 0;
            }

            public int getEnchantmentValue() {
                return 15;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:ingots/copper")));
            }
        }, 3, -1.5f,new Properties());
        broadsword = (Broadsword) InitItemSubClass.BROADSWORD.get();
        broadsword.setRarity(InitRarity.WHITE);
        broadsword.setItemClass(InitItemClass.MELEE);
        broadsword.setSwordItem(this);
        broadsword.setTier(InitTier.TIER_1);
    }

}
