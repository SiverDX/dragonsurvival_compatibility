package de.cadentem.dragonsurvival_compatibility.mixin.coldsweat;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import dev.momostudios.coldsweat.common.capability.ITemperatureCap;
import dev.momostudios.coldsweat.common.capability.PlayerTempCap;
import dev.momostudios.coldsweat.config.ConfigSettings;
import dev.momostudios.coldsweat.util.registries.ModDamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerTempCap.class)
public abstract class MixinPlayerTempCap implements ITemperatureCap {
    /**
     * Cave dragons take no damage from high temperature<br>
     * Sea dragons take no damage from low temperature<br>
     * Forest dragons take reduced damage from both temperature types<br>
     * <br>
     * Override due to it being a default interface method
     */
    @Override
    public void dealTempDamage(final LivingEntity target, final DamageSource source, float amount) {
        if (target instanceof Player player) {
            if (DragonUtils.isDragonType(player, DragonTypes.CAVE) && source == ModDamageSources.HOT) {
                return;
            } else if (DragonUtils.isDragonType(player, DragonTypes.SEA) && source == ModDamageSources.COLD) {
                return;
            } else if (DragonUtils.isDragonType(player, DragonTypes.FOREST)) {
                amount = amount * 0.65f;
            }
        }

        target.hurt(ConfigSettings.DAMAGE_SCALING.get() ? source.setScalesWithDifficulty() : source, amount);
    }
}
