package com.schokobaer.battleofgods.mechanics.item.subClass;

import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.init.InitSubClass;
import com.schokobaer.battleofgods.mechanics.item.AbstractSubClass;

import com.schokobaer.battleofgods.mechanics.item.SubClassMethods;
import com.schokobaer.battleofgods.mechanics.item.override.SwordItemOverride;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Broadsword extends SwordItem implements SubClassMethods {
private final AbstractSubClass subclass;

    public Broadsword(float attackDamageBonus, int enchantmentValue, Ingredient repairMaterial, int attackDamage, float attackSpeed, Properties properties, RegistryObject<com.schokobaer.battleofgods.mechanics.rarity.Rarity> rarity, RegistryObject<com.schokobaer.battleofgods.mechanics.tier.Tier> gameTier) {
        super(new Tier() {
            @Override
            public int getUses() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 1.6f;
            }

            @Override
            public float getAttackDamageBonus() {
                return attackDamageBonus;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return enchantmentValue;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return repairMaterial;
            }
        }, attackDamage, attackSpeed, properties);
        this.subclass = new AbstractSubClass(InitMainClass.MELEE,rarity,gameTier,"broadsword"){};
    }
    public Broadsword(Tier tier, int attackDamage, float attackSpeed, Properties properties, RegistryObject<com.schokobaer.battleofgods.mechanics.rarity.Rarity> rarity, RegistryObject<com.schokobaer.battleofgods.mechanics.tier.Tier> gameTier) {
        super(tier, attackDamage, attackSpeed, properties);
        this.subclass = new AbstractSubClass(InitMainClass.MELEE,rarity,gameTier,"broadsword"){};
    }

    public Broadsword(TagKey<Item> tag){
        super(new Tier() {
            @Override
            public int getUses() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 0;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        }, 0, 0, new Item.Properties());
        this.subclass = new AbstractSubClass("broadsword", InitMainClass.MELEE,tag){};
    }
    public AbstractSubClass getSubClassMethods(){
        return subclass;
    }


    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return subclass.getCraftingRemainingItem(itemstack);
    }
    @Override
    public Component getName(ItemStack stack) {
        return subclass.getName(stack);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, tooltip, flag);
        subclass.appendHoverText(itemstack, level, tooltip, flag);
    }
    @Override
    public boolean isRepairable(@NotNull ItemStack itemstack) {
        return subclass.isRepairable(itemstack);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return super.hasCraftingRemainingItem(stack);
    }

}

