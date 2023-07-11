package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import dev.momostudios.coldsweat.common.event.TempEffectsCommon;
import dev.momostudios.coldsweat.util.registries.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TempEffectsCommon.class)
public abstract class MixinTempEffectsCommon {
    @Redirect(method = {"onPlayerMine", "onPlayerTick", "onPlayerKnockback", "onHeal"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"), remap = false)
    private static boolean fakeHasEffect(final Player instance, final MobEffect effect) {
        if (effect == MobEffects.FIRE_RESISTANCE && DragonUtils.isDragonType(instance, DragonTypes.CAVE)) {
            return true;
        }

        if (effect == ModEffects.ICE_RESISTANCE && DragonUtils.isDragonType(instance, DragonTypes.SEA)) {
            return true;
        }

        return instance.hasEffect(effect);
    }
}
