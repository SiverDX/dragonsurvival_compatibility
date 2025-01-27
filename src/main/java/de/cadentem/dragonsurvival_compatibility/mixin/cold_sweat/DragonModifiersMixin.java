package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.registry.DragonModifiers;
import com.llamalad7.mixinextras.sugar.Local;
import de.cadentem.dragonsurvival_compatibility.compat.cold_sweat.ColdSweatUtils;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DragonModifiers.class, remap = false)
public abstract class DragonModifiersMixin {
    @Inject(method = "updateTypeModifiers", at = @At(value = "INVOKE", target = "Lby/dragonsurvivalteam/dragonsurvival/registry/DragonModifiers;buildSwimSpeedMod(Lby/dragonsurvivalteam/dragonsurvival/common/dragon_types/AbstractDragonType;)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"))
    private static void dragonsurvival_compatibility$grantModifiers(final Player player, final CallbackInfo callback, @Local final DragonStateHandler handler) {
        if (ServerConfig.COLD_SWEAT.get()) {
            ColdSweatUtils.addModifiers(player, handler);
        }
    }

    @Inject(method = "updateTypeModifiers", at = @At(value = "INVOKE", target = "Lby/dragonsurvivalteam/dragonsurvival/registry/DragonModifiers;getSwimSpeedModifier(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"))
    private static void dragonsurvival_compatibility$removeModifiers(final Player player, final CallbackInfo ci) {
        ColdSweatUtils.removeModifiers(player);
    }
}
