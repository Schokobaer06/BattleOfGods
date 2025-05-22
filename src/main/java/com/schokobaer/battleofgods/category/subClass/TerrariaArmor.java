package com.schokobaer.battleofgods.category.subClass;

import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.category.AbstractSubClass;
import com.schokobaer.battleofgods.category.SubClassMethods;
import com.schokobaer.battleofgods.category.mainClass.MainClass;
import com.schokobaer.battleofgods.category.mainClass.MainClasses;
import com.schokobaer.battleofgods.category.rarity.Rarity;
import com.schokobaer.battleofgods.category.tier.GameTier;
import com.schokobaer.battleofgods.category.tier.Tiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class TerrariaArmor extends ArmorItem implements GeoItem, SubClassMethods {
    private final int defense;
    private final Map<Attribute, AttributeModifier> attributeModifiers = new HashMap<>();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<AbstractSubClass> subClass;


    /**
     * Abstract ArmorItem class for BattleOfGods-Armor.
     *
     * @param material   For more advanced configuration
     * @param type       Helmet, Chestplate, Leggings, Boots
     * @param properties Item properties
     * @param rarity     the rarity of the item
     * @param gameTier   GameTier of the item
     */
    public TerrariaArmor(String name, ArmorMaterial material, Type type, Properties properties, Rarity rarity, GameTier gameTier) {
        super(material, type, properties);
        this.defense = material.getDefenseForType(type);
        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.ARMOR);
            sb.setRarity(rarity);
            sb.setGameTier(gameTier);
            return sb;
        };

    }

    /**
     * Abstract ArmorItem class for BattleOfGods-Armor.
     *
     * @param name       name of the armor
     * @param defense    Defense value for each armor piece (max 4 values)
     *                   {a b c d} = {Boots, Leggings, Chestplate, Helmet}
     * @param soundEvent Equip sound
     * @param type       Armor type (Helmet, Chestplate, Leggings, Boots)
     * @param tier       the tier of the item
     * @param gameTier   GameTier of the item
     */
    public TerrariaArmor(String name, int[] defense, SoundEvent soundEvent, Type type, Tiers tier, GameTier gameTier) {
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
                return tier.getEnchantmentValue();
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
        }, type, new Properties());
        this.defense = defense[type.getSlot().getIndex()];
        this.subClass = () -> {
            AbstractSubClass sb = new AbstractSubClass() {
            };
            sb.setMainClass(MainClasses.ARMOR);
            sb.setRarity(tier.getRarity());
            sb.setGameTier(gameTier);
            return sb;
        };
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
    public TerrariaArmor addAttributeModifier(Attribute attribute, double amount, AttributeModifier.Operation operation) {
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
        return subClass.get().getName(super.getName(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        subClass.get().appendHoverText(stack, level, tooltip, flag);

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

    public boolean isAutoSwing() {
        return false;
    }

    public void setAutoSwing(boolean autoSwing) {
    }

    public MainClass getMainClass() {
        return subClass.get().getMainClass();
    }

    public Rarity getRarity() {
        return subClass.get().getRarity();
    }

    public void setRarity(Rarity rarity) {
        subClass.get().setRarity(rarity);
    }

    public GameTier getGameTier() {
        return subClass.get().getGameTier();
    }

    @Override
    public net.minecraft.world.item.Rarity getRarity(ItemStack stack) {
        Item item = stack.getItem();
        if (item.getClass().getSuperclass() != null && SubClassMethods.class.isAssignableFrom(item.getClass().getSuperclass())) {
            SubClassMethods subClassItem = (SubClassMethods) item.getClass().getSuperclass().cast(item);
            return subClassItem.getRarity().asMinecraftRarity();
        }
        return super.getRarity(stack);
    }

    public int getKnockback() {
        return 0;
    }
}
