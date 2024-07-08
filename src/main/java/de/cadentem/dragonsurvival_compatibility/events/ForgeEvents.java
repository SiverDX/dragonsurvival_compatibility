package de.cadentem.dragonsurvival_compatibility.events;

import de.cadentem.dragonsurvival_compatibility.compat.bettercombat.AnimationUtils;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgeEvents {
    @SubscribeEvent
    public static void decrementHideModelTicks(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side.isClient()) {
            AnimationUtils.decrement(event.player);
        }
    }
}
