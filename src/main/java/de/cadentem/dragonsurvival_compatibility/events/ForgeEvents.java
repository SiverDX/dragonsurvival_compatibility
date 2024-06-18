package de.cadentem.dragonsurvival_compatibility.events;

import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void decrementHideModelTicks(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side.isClient() && event.player == Minecraft.getInstance().player && Utils.HIDE_MODEL_LENGTH > 0) {
            Utils.HIDE_MODEL_LENGTH--;
        }
    }
}
