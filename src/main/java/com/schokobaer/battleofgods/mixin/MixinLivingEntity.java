package com.schokobaer.battleofgods.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @ModifyVariable(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getSpeed()F"),
            ordinal = 0
    )
    private float modifyMovementSpeed(float original) {
        if ((Object) this instanceof Player player) {
            player.isSprinting();
        }
        return original;
    }
}