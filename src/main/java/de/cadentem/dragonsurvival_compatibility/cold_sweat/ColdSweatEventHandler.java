package de.cadentem.dragonsurvival_compatibility.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import dev.momostudios.coldsweat.util.registries.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ColdSweatEventHandler {
    private static final int DURATION_IN_TICKS = 3 * 20; // Seconds * ticks
    // Setting it to a high value could be a problem if other mods modify its duration or amplification
    // To make the icon show as an infinite effect (if it's shown somewhere) the seconds would need to be around 3_000

    private static final MobEffectInstance ICE_RESISTANCE = new MobEffectInstance(ModEffects.ICE_RESISTANCE, DURATION_IN_TICKS, 0, true, false, false);
    private static final MobEffectInstance FIRE_RESISTANCE = new MobEffectInstance(MobEffects.FIRE_RESISTANCE, DURATION_IN_TICKS, 0, true, false, false);

    // FIXME :: What if the player disabled the effect of these granting immunity?
    @SubscribeEvent(priority = EventPriority.HIGH) // Tick before Cold Sweat
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (ServerConfig.ENABLE_COLD_SWEAT.get() && event.player.tickCount % 20 == 0) {
            if (event.phase == TickEvent.Phase.END) {
                MobEffectInstance iceResistance = event.player.getEffect(ModEffects.ICE_RESISTANCE);
                MobEffectInstance fireResistance = event.player.getEffect(MobEffects.FIRE_RESISTANCE);

                if (DragonUtils.isDragonType(event.player, DragonTypes.SEA)) {
                    if (iceResistance == null) {
                        event.player.addEffect(ICE_RESISTANCE);
                    } else if (iceResistance.getAmplifier() == 0 && iceResistance.getDuration() <= DURATION_IN_TICKS) {
                        iceResistance.duration = DURATION_IN_TICKS;
                    }
                } else if (iceResistance != null && iceResistance.getAmplifier() == 0 && iceResistance.getDuration() <= DURATION_IN_TICKS) {
                    event.player.removeEffect(ModEffects.ICE_RESISTANCE);
                }

                if (DragonUtils.isDragonType(event.player, DragonTypes.CAVE)) {
                    if (fireResistance == null) {
                        event.player.addEffect(FIRE_RESISTANCE);
                    } else if (fireResistance.getAmplifier() == 0 && fireResistance.getDuration() <= DURATION_IN_TICKS) {
                        fireResistance.duration = DURATION_IN_TICKS;
                    }
                } else if (fireResistance != null && fireResistance.getAmplifier() == 0 && fireResistance.getDuration() <= DURATION_IN_TICKS) {
                    event.player.removeEffect(MobEffects.FIRE_RESISTANCE);
                }
            }
        }
    }
}
