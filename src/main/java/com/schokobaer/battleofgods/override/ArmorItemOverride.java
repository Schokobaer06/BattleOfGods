package com.schokobaer.battleofgods.override;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.subClass.AbstractSubClass;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

public class ArmorItemOverride extends ItemOverride implements Equipable {
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            return ArmorItemOverride.dispenseArmor(blockSource, itemStack) ? itemStack : super.execute(blockSource, itemStack);
        }
    };
    private static final EnumMap<ArmorItemOverride.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItemOverride.Type.class), (uuidMap) -> {
        uuidMap.put(ArmorItemOverride.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        uuidMap.put(ArmorItemOverride.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        uuidMap.put(ArmorItemOverride.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        uuidMap.put(ArmorItemOverride.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });
    protected final ArmorItemOverride.Type armorType;
    protected final float knockbackResistanceValue;
    protected final ArmorMaterialOverride armorMaterial;
    private final int defenseValue;
    private final float toughnessValue;
    private final Multimap<Attribute, AttributeModifier> defaultAttributeModifiers;

    public ArmorItemOverride(ArmorMaterialOverride material, ArmorItemOverride.Type type, Item.Properties properties, AbstractSubClass subClass) {
        super(properties.defaultDurability(material.getDurabilityForType(type)), subClass);
        this.armorMaterial = material;
        this.armorType = type;
        this.defenseValue = material.getDefenseForType(type);
        this.toughnessValue = material.getToughness();
        this.knockbackResistanceValue = material.getKnockbackResistance();
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", this.defenseValue, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", this.toughnessValue, AttributeModifier.Operation.ADDITION));
        if (this.knockbackResistanceValue > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", this.knockbackResistanceValue, AttributeModifier.Operation.ADDITION));
        }

        this.defaultAttributeModifiers = builder.build();
    }

    public static boolean dispenseArmor(BlockSource blockSource, ItemStack itemStack) {
        BlockPos targetPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> entities = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(targetPos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(itemStack)));
        if (entities.isEmpty()) {
            return false;
        } else {
            LivingEntity targetEntity = entities.get(0);
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
            ItemStack splitStack = itemStack.split(1);
            targetEntity.setItemSlot(equipmentSlot, splitStack);
            if (targetEntity instanceof Mob mobEntity) {
                mobEntity.setDropChance(equipmentSlot, 2.0F);
                mobEntity.setPersistenceRequired();
            }

            return true;
        }
    }

    public ArmorItemOverride.Type getArmorType() {
        return this.armorType;
    }

    public int getEnchantmentValue() {
        return this.armorMaterial.getEnchantmentValue();
    }

    public ArmorMaterialOverride getArmorMaterial() {
        return this.armorMaterial;
    }

    public boolean isValidRepairItem(ItemStack armorStack, ItemStack repairStack) {
        return this.armorMaterial.getRepairIngredient().test(repairStack) || super.isValidRepairItem(armorStack, repairStack);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == this.armorType.getSlot() ? this.defaultAttributeModifiers : super.getDefaultAttributeModifiers(slot);
    }

    public int getDefenseValue() {
        return this.defenseValue;
    }

    public float getToughnessValue() {
        return this.toughnessValue;
    }

    public EquipmentSlot getEquipmentSlot() {
        return this.armorType.getSlot();
    }

    public SoundEvent getEquipSound() {
        return this.getArmorMaterial().getEquipSound();
    }

    public enum Type {
        HELMET(EquipmentSlot.HEAD, "helmet"),
        CHESTPLATE(EquipmentSlot.CHEST, "chestplate"),
        LEGGINGS(EquipmentSlot.LEGS, "leggings"),
        BOOTS(EquipmentSlot.FEET, "boots");

        private final EquipmentSlot slot;
        private final String name;

        Type(EquipmentSlot slot, String name) {
            this.slot = slot;
            this.name = name;
        }

        public EquipmentSlot getSlot() {
            return this.slot;
        }

        public String getName() {
            return this.name;
        }
    }
}