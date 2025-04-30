package com.schokobaer.battleofgods.mechanics.item;


import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.mechanics.item.override.ItemOverride;
import com.schokobaer.battleofgods.mechanics.item.override.SwordItemOverride;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import com.schokobaer.battleofgods.mechanics.tier.Tier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractSubClass implements SubClassMethods {
    private final RegistryObject<MainClass> mainClass;
    private RegistryObject<Rarity> rarity = null;
    private RegistryObject<Tier> tier = null;
    private RegistryObject<ItemOverride> subClass = null;
    private final String name;
    private TagKey<ItemOverride> tag = null;

    protected AbstractSubClass(RegistryObject<MainClass> mainClass, @NotNull RegistryObject<Rarity> rarity, RegistryObject<Tier> tier, RegistryObject<ItemOverride> subClass) {
        this.mainClass = mainClass;
        this.rarity = rarity;
        this.tier = tier;
        this.subClass = subClass;
        this.name = subClass.getId().getPath();
    }
    protected AbstractSubClass(String name, RegistryObject<MainClass> mainClass, TagKey<ItemOverride> tag) {
        this.tag = tag;
        this.name = name;
        this.mainClass = mainClass;

    }


    public MainClass getMainClass() {
        return this.mainClass.get();
    }

    public Rarity getRarity() {
        return this.rarity.get();
    }

    public Tier getTier() {
        return this.tier.get();
    }

    public ItemOverride getSubClass(){ return this.subClass.get(); }

    public TagKey<ItemOverride> getTag() {
        return this.tag;
    }



    @Override
    public Component getName(Component name) {
        return name.copy().withStyle(Style.EMPTY.withColor(this.getRarity().getColor()));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<net.minecraft.network.chat.Component> tooltip, TooltipFlag flag) {
        //Rarity
        tooltip.add(Component.translatable("rarity.battleofgods." +
                this.getRarity().getDisplayName().toLowerCase()).setStyle(
                        Style.EMPTY.withBold(true)
                                .withColor(this.getRarity().getColor())
                                .withItalic(true)
        ));
        //Damage
        if (itemstack.getItem() instanceof SwordItemOverride swordItem) {
            float damage = swordItem.getDamage()+1;
            String damageText = (damage % 1 == 0) ? String.valueOf((int) damage) : String.valueOf(damage);
            tooltip.add(Component.literal(damageText + " " + this.getMainClass().getName() + " ")
                    .append(Component.translatable("tooltip.battleofgods.damage"))
                    .withStyle(ChatFormatting.DARK_GREEN));
        }
        //Speed
        // Get the attack speed attribute (default to 4.0 if missing)
        double attackSpeed = itemstack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(Attributes.ATTACK_SPEED).stream()
                .mapToDouble(AttributeModifier::getAmount)
                .sum() + 4; // +4.0 because attack speed is offset in Minecraft

        //Convert to Terraria useTime
        String speedText = getSpeed(attackSpeed);

        tooltip.add(Component.translatable("tooltip.battleofgods." + speedText).withStyle(ChatFormatting.DARK_GREEN));

        //Knockback

    }

    private static @NotNull String getSpeed(double attackSpeed) {
        int useTime = (int) Math.round(20.0 / attackSpeed);

        // Determine speed description
        String speedText;
        if (useTime <= 5) speedText = "weapon_speed_insanely_fast";
        else if (useTime <= 10) speedText = "weapon_speed_very_fast";
        else if (useTime <= 15) speedText = "weapon_speed_fast";
        else if (useTime <= 20) speedText = "weapon_speed_average";
        else if (useTime <= 25) speedText = "weapon_speed_slow";
        else if (useTime <= 30) speedText = "weapon_speed_very_slow";
        else speedText = "weapon_speed_extremely_slow";
        return speedText;
    }


    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }
    @Override
    public boolean isRepairable(@NotNull ItemStack itemstack) {
        return false;
    }
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(itemstack.getItem());
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }
}
