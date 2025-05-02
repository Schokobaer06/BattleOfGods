package com.schokobaer.battleofgods.override;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.schokobaer.battleofgods.classes.AbstractSubClass;
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
            return com.schokobaer.battleofgods.override.ArmorItemOverride.dispenseArmor(blockSource, itemStack) ? itemStack : super.execute(blockSource, itemStack);
        }
    };
    private static final EnumMap<com.schokobaer.battleofgods.override.ArmorItemOverride.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(com.schokobaer.battleofgods.override.ArmorItemOverride.Type.class), (uuidMap) -> {
        uuidMap.put(com.schokobaer.battleofgods.override.ArmorItemOverride.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        uuidMap.put(com.schokobaer.battleofgods.override.ArmorItemOverride.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        uuidMap.put(com.schokobaer.battleofgods.override.ArmorItemOverride.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        uuidMap.put(com.schokobaer.battleofgods.override.ArmorItemOverride.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });
    protected final com.schokobaer.battleofgods.override.ArmorItemOverride.Type type;
    protected final float knockbackResistance;
    protected final ArmorMaterialOverride material;
    private final int defense;
    private final float toughness;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ArmorItemOverride(ArmorMaterialOverride material, com.schokobaer.battleofgods.override.ArmorItemOverride.Type type, Item.Properties properties, AbstractSubClass subClass) {
        super(properties.defaultDurability(material.getDurabilityForType(type)), subClass);
        this.material = material;
        this.type = type;
        this.defense = material.getDefenseForType(type);
        this.toughness = material.getToughness();
        this.knockbackResistance = material.getKnockbackResistance();
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double) this.defense, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double) this.toughness, AttributeModifier.Operation.ADDITION));
        if (this.knockbackResistance > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double) this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }

        this.defaultModifiers = builder.build();
    }

    public static boolean dispenseArmor(BlockSource blockSource, ItemStack itemStack) {
        BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> entities = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockPos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(itemStack)));
        if (entities.isEmpty()) {
            return false;
        } else {
            LivingEntity livingEntity = entities.get(0);
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
            ItemStack splitStack = itemStack.split(1);
            livingEntity.setItemSlot(equipmentSlot, splitStack);
            if (livingEntity instanceof Mob) {
                ((Mob) livingEntity).setDropChance(equipmentSlot, 2.0F);
                ((Mob) livingEntity).setPersistenceRequired();
            }

            return true;
        }
    }

    public com.schokobaer.battleofgods.override.ArmorItemOverride.Type getType() {
        return this.type;
    }

    public int getEnchantmentValue() {
        return this.material.getEnchantmentValue();
    }

    public ArmorMaterialOverride getMaterial() {
        return this.material;
    }

    public boolean isValidRepairItem(ItemStack stackToRepair, ItemStack repairMaterial) {
        return this.material.getRepairIngredient().test(repairMaterial) || super.isValidRepairItem(stackToRepair, repairMaterial);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == this.type.getSlot() ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    public int getDefense() {
        return this.defense;
    }

    public float getToughness() {
        return this.toughness;
    }

    public EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }

    public SoundEvent getEquipSound() {
        return this.getMaterial().getEquipSound();
    }

    public static enum Type {
        HELMET(EquipmentSlot.HEAD, "helmet"),
        CHESTPLATE(EquipmentSlot.CHEST, "chestplate"),
        LEGGINGS(EquipmentSlot.LEGS, "leggings"),
        BOOTS(EquipmentSlot.FEET, "boots");

        private final EquipmentSlot slot;
        private final String name;

        private Type(EquipmentSlot slot, String name) {
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