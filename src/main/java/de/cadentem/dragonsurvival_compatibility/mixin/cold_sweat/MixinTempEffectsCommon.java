package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.momosoftworks.coldsweat.common.event.TempEffectsCommon;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TempEffectsCommon.class)
public abstract class MixinTempEffectsCommon {
    @WrapOperation(method = {"onPlayerMine", "onPlayerTick", "onPlayerKnockback", "onHeal"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z", ordinal = 0))
    private static boolean dragonsurvival_compatibility$fakeIceResistance(final Player instance, final MobEffect effect, final Operation<Boolean> original) {
        if (ServerConfig.COLD_SWEAT.get()) {
            if (DragonUtils.isDragonType(instance, DragonTypes.SEA)) {
                return true;
            }
        }

        return original.call(instance, effect);
    }
}
