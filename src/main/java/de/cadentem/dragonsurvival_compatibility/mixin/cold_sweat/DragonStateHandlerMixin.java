package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.compat.cold_sweat.ColdSweatUtils;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DragonStateHandler.class, remap = false)
public class DragonStateHandlerMixin {
    @Inject(method = "updateModifiers", at = @At(value = "RETURN"))
    private void addTemperatureModifier(double size, final Player player, final CallbackInfo callback) {
        DragonStateHandler handler = DragonUtils.getHandler(player);

        if (!handler.isDragon()) {
            ColdSweatUtils.removeModifiers(player);
        } else if (ServerConfig.COLD_SWEAT.get()) {
            ColdSweatUtils.addModifiers(player, handler);
        }
    }
}
