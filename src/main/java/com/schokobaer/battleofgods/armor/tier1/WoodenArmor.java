package com.schokobaer.battleofgods.armor.tier1;

import com.schokobaer.battleofgods.armor.TerrariaArmorItem;
import com.schokobaer.battleofgods.init.InitRarity;
import com.schokobaer.battleofgods.init.InitTier;
import net.minecraft.world.item.ArmorMaterial;

public class WoodenArmor extends TerrariaArmorItem {

    public WoodenArmor(ArmorMaterial material, Type type, Properties properties) {
        super(
                material,
                type,
                properties,
                getDefenseForType(type),  // Defense-Wert basierend auf Typ
                InitRarity.WHITE,// Deine Registry für Raritäten
                InitTier.TIER_1           // Deine Registry für Tiers
        );
    }

    private static int getDefenseForType(Type type) {
        return switch (type) {
            case HELMET -> 0;
            case CHESTPLATE -> 1;
            case LEGGINGS -> 1;
            case BOOTS -> 0;
        };
    }
}
