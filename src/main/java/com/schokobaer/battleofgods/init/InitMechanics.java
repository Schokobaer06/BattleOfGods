package com.schokobaer.battleofgods.init;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import com.schokobaer.battleofgods.BattleofgodsMod;
public class InitMechanics {
    public static final DeferredRegister<Rarity> RARITIES = DeferredRegister.create(new ResourceLocation(BattleofgodsMod.MODID, "rarities"), BattleofgodsMod.MODID);
    public static final RegistryObject<Rarity> GRAY = RARITIES.register("gray", () -> new Rarity(0x828282));
    public static final RegistryObject<Rarity> WHITE = RARITIES.register("white", () -> new Rarity("#FFFFFF"));
}
