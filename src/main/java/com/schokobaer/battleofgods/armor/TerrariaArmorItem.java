package com.schokobaer.battleofgods.armor;

import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.mainClass.MainClass;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.tier.Tier;
import com.schokobaer.battleofgods.init.InitMainClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;
import java.util.function.Consumer;

public abstract class TerrariaArmorItem extends ArmorItem implements GeoItem {
    private final int defense;
    private final Map<Attribute, AttributeModifier> attributeModifiers = new HashMap<>();
    private final RegistryObject<Rarity> rarity;
    private final RegistryObject<Tier> tier;
    private final RegistryObject<MainClass> mainClass = InitMainClass.ARMOR;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    /**
     * Abstract ArmorItem class for BattleOfGods-Armor.
     *
     * @param material   For more advanced configuration
     * @param type       Helmet, Chestplate, Leggings, Boots
     * @param properties Item properties
     * @param rarity     Rarity of the item
     * @param tier       Tier of the item
     */
    public TerrariaArmorItem(String name, ArmorMaterial material, Type type, Properties properties, RegistryObject<Rarity> rarity, RegistryObject<Tier> tier) {
        super(material, type, properties);
        this.defense = material.getDefenseForType(type);
        this.rarity = rarity;
        this.tier = tier;
    }

    /**
     * Abstract ArmorItem class for BattleOfGods-Armor.
     *
     * @param name             name of the armor
     * @param defense          Defense value for each armor piece (max 4 values)
     *                         {a b c d} = {Boots, Leggings, Chestplate, Helmet}
     * @param soundEvent       Equip sound
     * @param enchantmentValue Enchantment value when enchanting
     * @param type             Armor type (Helmet, Chestplate, Leggings, Boots)
     * @param properties       Item properties
     * @param rarity           Rarity of the item
     * @param tier             Tier of the item
     */
    public TerrariaArmorItem(String name, int[] defense, SoundEvent soundEvent, int enchantmentValue, Type type, Properties properties, RegistryObject<Rarity> rarity, RegistryObject<Tier> tier) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type type1) {
                return 256;
            }

            @Override
            public int getDefenseForType(Type type1) {
                return defense[type1.ordinal()];
            }

            @Override
            public int getEnchantmentValue() {
                return enchantmentValue;
            }

            @Override
            public SoundEvent getEquipSound() {
                return Objects.requireNonNullElse(soundEvent, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic")));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public float getToughness() {
                return 0;
            }

            @Override
            public float getKnockbackResistance() {
                return 0;
            }
        }, type, properties);
        this.defense = defense[type.getSlot().getIndex()];
        this.rarity = rarity;
        this.tier = tier;
    }

    public Rarity getRarity() {
        return this.rarity.get();
    }

    public Tier getTier() {
        return this.tier.get();
    }

    public MainClass getMainClass() {
        return this.mainClass.get();
    }

    // Getter für Defense (Terraria-Style)
    public int getDefenseValue() {
        return defense;
    }

    // Deaktiviere Minecrafts Standard-Rüstungsberechnung
    @Deprecated
    @Override
    public int getDefense() {
        return 0;
    }

    // Füge individuelle Attribute hinzu
    public TerrariaArmorItem addAttributeModifier(Attribute attribute, double amount, AttributeModifier.Operation operation) {
        UUID uuid = UUID.randomUUID();
        attributeModifiers.put(attribute, new AttributeModifier(uuid, "Armor Modifier", amount, operation));
        return this;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        if (slot == this.type.getSlot()) {
            attributeModifiers.forEach((attr, mod) -> {
                AttributeModifier slotSpecificModifier = new AttributeModifier(
                        mod.getId(),
                        mod.getName(),
                        mod.getAmount(),
                        mod.getOperation()
                );
                modifiers.put(attr, slotSpecificModifier);
            });
        }
        return modifiers;
    }


    @Override
    public Component getName(ItemStack stack) {
        Component name = super.getName(stack);
        return name.copy().withStyle(Style.EMPTY.withColor(this.getRarity().getColor()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        //Rarity
        tooltip.add(Component.translatable("rarity.battleofgods." +
                this.getRarity().getDisplayName().toLowerCase()).setStyle(
                Style.EMPTY.withBold(true)
                        .withColor(this.getRarity().getColor())
                        .withItalic(true)
        ));
        //Defense
        if (tooltip.size() > 2)
            tooltip.add(2, Component.literal(getDefenseValue() + " ")
                    .append(Component.translatable("tooltip.battleofgods.armor"))
                    .withStyle(ChatFormatting.DARK_GREEN));
        else
            tooltip.add(Component.literal(this.getDefenseValue() + " ")
                    .append(Component.translatable("tooltip.battleofgods.armor"))
                    .withStyle(ChatFormatting.DARK_GREEN));
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

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public abstract void initializeClient(Consumer<IClientItemExtensions> consumer);
}
