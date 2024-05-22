package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.registry.DragonModifiers;
import com.llamalad7.mixinextras.sugar.Local;
import de.cadentem.dragonsurvival_compatibility.cold_sweat.ColdSweatUtils;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DragonModifiers.class, remap = false)
public class MixinDragonStateHandler {
    @Inject(method = "updateModifiers", at = @At(value = "INVOKE", target = "Lby/dragonsurvivalteam/dragonsurvival/registry/DragonModifiers;buildHealthMod(D)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"))
    private static void addTemperatureModifier(final Player player, final CallbackInfo callback, @Local final DragonStateHandler handler) {
        if (!ServerConfig.COLD_SWEAT.get()) {
            return;
        }

        ColdSweatUtils.addModifiers(player, handler);
    }

    @Inject(method = "updateModifiers", at = @At(value = "INVOKE", target = "Lby/dragonsurvivalteam/dragonsurvival/registry/DragonModifiers;getHealthModifier(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"))
    private static void removeTemperatureModifier(final Player player, final CallbackInfo callback) {
        ColdSweatUtils.removeModifiers(player);
    }
}
