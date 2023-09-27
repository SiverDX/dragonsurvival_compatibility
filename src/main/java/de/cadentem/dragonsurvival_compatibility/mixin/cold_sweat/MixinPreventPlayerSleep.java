package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.AbstractDragonType;
import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.common.event.PreventPlayerSleep;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.util.math.CSMath;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PreventPlayerSleep.class)
public abstract class MixinPreventPlayerSleep {
    /** Allow dragons to sleep in certain temperature conditions */
    @Inject(method = "onTrySleep", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void sleepForDragonCheck(final PlayerSleepInBedEvent event, final CallbackInfo callback) {
        if (ServerConfig.ENABLE_COLD_SWEAT.get()) {
            if (event.getResultStatus() == null && ConfigSettings.CHECK_SLEEP_CONDITIONS.get()) {
                Player player = event.getEntity();

                AbstractDragonType dragonType = DragonUtils.getDragonType(player);

                if (dragonType != null) {
                    double bodyTemp = Temperature.get(player, Temperature.Type.BODY);
                    double worldTemp = Temperature.get(player, Temperature.Type.WORLD);
                    double minTemp = ConfigSettings.MIN_TEMP.get() + Temperature.get(player, Temperature.Type.BURNING_POINT);
                    double maxTemp = ConfigSettings.MAX_TEMP.get() + Temperature.get(player, Temperature.Type.FREEZING_POINT);

                    boolean isBodyTemperatureValid = CSMath.isBetween(bodyTemp, -100.0, 100.0);

                    if ((!isBodyTemperatureValid && bodyTemp < 0 || worldTemp < minTemp) && dragonType.getTypeName().equals(DragonTypes.SEA.getTypeName())) {
                        // Ignores low temperature checks
                        callback.cancel();
                    } else if ((!isBodyTemperatureValid && bodyTemp > 0 || worldTemp > maxTemp) && dragonType.getTypeName().equals(DragonTypes.CAVE.getTypeName())) {
                        // Ignores high temperature checks
                        callback.cancel();
                    } else if (!isBodyTemperatureValid && bodyTemp + 20 > 0 && bodyTemp - 20 < 100 || (worldTemp + 20 > minTemp && worldTemp - 20 < maxTemp) && dragonType.getTypeName().equals(DragonTypes.FOREST.getTypeName())) {
                        // Buffer of 20 for temperature checks
                        callback.cancel();
                    }
                }
            }
        }
    }
}
