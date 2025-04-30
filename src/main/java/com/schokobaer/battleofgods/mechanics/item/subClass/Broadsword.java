package com.schokobaer.battleofgods.mechanics.item.subClass;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitMainClass;
import com.schokobaer.battleofgods.init.InitSubClass;
import com.schokobaer.battleofgods.mechanics.item.AbstractSubClass;
import com.schokobaer.battleofgods.mechanics.item.override.ItemOverride;
import com.schokobaer.battleofgods.mechanics.item.override.SwordItemOverride;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class Broadsword extends SwordItemOverride {
    public Broadsword(float attackDamageBonus, int enchantmentValue, Ingredient repairMaterial, int attackDamage, float attackSpeed, Properties properties, RegistryObject<com.schokobaer.battleofgods.mechanics.rarity.Rarity> rarity, RegistryObject<com.schokobaer.battleofgods.mechanics.tier.Tier> gameTier) {
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
        }, attackDamage-1, attackSpeed-4, properties, new AbstractSubClass(InitMainClass.MELEE, rarity, gameTier, InitSubClass.BROADSWORD) {});
    }
    public Broadsword(Tier tier, int attackDamage, float attackSpeed, Properties properties, RegistryObject<com.schokobaer.battleofgods.mechanics.rarity.Rarity> rarity, RegistryObject<com.schokobaer.battleofgods.mechanics.tier.Tier> gameTier) {
        super(tier, attackDamage, attackSpeed, properties, new AbstractSubClass(InitMainClass.MELEE, rarity, gameTier, InitSubClass.BROADSWORD) {});}

    public Broadsword(TagKey<ItemOverride> tag){
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
        }, 0, 0, new Properties(), new AbstractSubClass("broadsword", InitMainClass.MELEE, tag) {});
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return super.hasCraftingRemainingItem(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, tooltip, flag);

        //Set Damage Tooltip Customized
        for (int i = 0; i < tooltip.size(); i++)
            if (tooltip.get(i).getString().contains(Component.translatable("tooltip.battleofgods.damage").getString())) {
                if (itemstack.getItem() instanceof SwordItemOverride swordItem) {
                    float damage = swordItem.getDamage()+1;
                    String damageText = (damage % 1 == 0) ? String.valueOf((int) damage) : String.valueOf(damage);
                    tooltip.set(i,
                            Component.literal(damageText + " true " + this.getSubClassMethods().getMainClass().getName() + " ")
                                    .append(Component.translatable("tooltip.battleofgods.damage"))
                                    .withStyle(ChatFormatting.DARK_GREEN));
                }
            }
    }
}

