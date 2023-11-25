package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import de.cadentem.dragonsurvival_compatibility.cold_sweat.ColdSweatUtils;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DragonStateHandler.class, remap = false)
public class MixinDragonStateHandler {
    @Inject(method = "updateModifiers", at = @At(value = "INVOKE", target = "Lby/dragonsurvivalteam/dragonsurvival/registry/DragonModifiers;buildHealthMod(D)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"))
    private void addTemperatureModifier(double size, final Player player, final CallbackInfo callback) {
        if (!ServerConfig.COLD_SWEAT.get()) {
            return;
        }

        ColdSweatUtils.addModifiers(player, (DragonStateHandler) (Object) this);
    }

    @Inject(method = "updateModifiers", at = @At(value = "INVOKE", target = "Lby/dragonsurvivalteam/dragonsurvival/registry/DragonModifiers;getHealthModifier(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"))
    private void removeTemperatureModifier(double size, final Player player, final CallbackInfo callback) {
        ColdSweatUtils.removeModifiers(player);
    }
}
