package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.momosoftworks.coldsweat.common.capability.ITemperatureCap;
import com.momosoftworks.coldsweat.common.capability.PlayerTempCap;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import com.momosoftworks.coldsweat.util.registries.ModDamageSources;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
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
        if (ServerConfig.COLD_SWEAT.get()) {
            if (target instanceof Player player) {
                if (DragonUtils.isDragonType(player, DragonTypes.CAVE) && source == ModDamageSources.HOT) {
                    return;
                } else if (DragonUtils.isDragonType(player, DragonTypes.SEA) && source == ModDamageSources.COLD) {
                    return;
                } else if (DragonUtils.isDragonType(player, DragonTypes.FOREST)) {
                    amount = amount * 0.65f;
                }
            }
        }

        target.hurt(ConfigSettings.DAMAGE_SCALING.get() ? source.setScalesWithDifficulty() : source, amount);
    }
}
