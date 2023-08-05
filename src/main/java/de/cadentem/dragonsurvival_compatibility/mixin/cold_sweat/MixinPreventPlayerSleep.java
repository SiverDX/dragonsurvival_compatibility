package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import dev.momostudios.coldsweat.api.util.Temperature;
import dev.momostudios.coldsweat.common.event.PreventPlayerSleep;
import dev.momostudios.coldsweat.config.ConfigSettings;
import dev.momostudios.coldsweat.util.math.CSMath;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PreventPlayerSleep.class)
public abstract class MixinPreventPlayerSleep {
    @Inject(method = "onTrySleep", at = @At(value = "HEAD"), cancellable = true)
    private static void sleepForDragonCheck(final PlayerSleepInBedEvent event, final CallbackInfo callback) {
        if (ServerConfig.ENABLE_COLD_SWEAT.get()) {
            if (event.getResultStatus() == null && ConfigSettings.CHECK_SLEEP_CONDITIONS.get()) {
                Player player = event.getEntity();

                if (DragonUtils.isDragon(player)) {
                    double bodyTemp = Temperature.get(player, Temperature.Type.BODY);
                    double worldTemp = Temperature.get(player, Temperature.Type.WORLD);
                    double minTemp = ConfigSettings.MIN_TEMP.get() + Temperature.get(player, Temperature.Type.BURNING_POINT);
                    double maxTemp = ConfigSettings.MAX_TEMP.get() + Temperature.get(player, Temperature.Type.FREEZING_POINT);

                    boolean isBodyTemperatureValid = CSMath.isBetween(bodyTemp, -100.0, 100.0);

                    if ((!isBodyTemperatureValid && bodyTemp < 0 || worldTemp < minTemp) && DragonUtils.isDragonType(player, DragonTypes.SEA)) {
                        callback.cancel();
                    } else if ((!isBodyTemperatureValid && bodyTemp > 0 || worldTemp > maxTemp) && DragonUtils.isDragonType(player, DragonTypes.CAVE)) {
                        callback.cancel();
                    }
                }
            }
        }
    }
}
