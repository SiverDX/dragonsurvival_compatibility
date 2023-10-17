package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.momosoftworks.coldsweat.client.event.TempEffectsClient;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TempEffectsClient.class, remap = false)
public abstract class MixinTempEffectsClient {
    @Shadow static int HOT_IMMUNITY;
    @Shadow static int COLD_IMMUNITY;

    /** @reason Modifies the effect of temperatures on the dragon types
     * <ul>
     * <li>Cave dragons have no negative effects from high temperature</li>
     * <li>Sea dragons have no negative effects from low temperature</li>
     * <li>Forest dragons have resistance against both temperatures</li>
     * </ul>
     */
    @Inject(method = "onClientTick", at = @At("RETURN"))
    private static void modifyImmunity(final TickEvent.ClientTickEvent event, final CallbackInfo callback) {
        if (/* Seems to be ticked early */ ServerConfig.SPEC.isLoaded() && ServerConfig.COLD_SWEAT.get()) {
            LocalPlayer player = Minecraft.getInstance().player;

            if (HOT_IMMUNITY < 4) {
                if (DragonUtils.isDragonType(player, DragonTypes.CAVE)) {
                    HOT_IMMUNITY = Math.max(4, HOT_IMMUNITY);
                } else if (DragonUtils.isDragonType(player, DragonTypes.FOREST)) {
                    HOT_IMMUNITY = Math.min(4, COLD_IMMUNITY + 2);
                }
            }

            if (COLD_IMMUNITY < 4) {
                if (DragonUtils.isDragonType(player, DragonTypes.SEA)) {
                    COLD_IMMUNITY = Math.max(4, COLD_IMMUNITY);
                } else if (DragonUtils.isDragonType(player, DragonTypes.FOREST)) {
                    COLD_IMMUNITY = Math.min(4, COLD_IMMUNITY + 2);
                }
            }
        }
    }
}
