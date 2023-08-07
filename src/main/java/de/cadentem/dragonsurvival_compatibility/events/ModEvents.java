package de.cadentem.dragonsurvival_compatibility.events;

import de.cadentem.dragonsurvival_compatibility.DragonSurvivalCompatibility;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DragonSurvivalCompatibility.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void reloadConfiguration(final ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == ClientConfig.SPEC) {
            ClientConfig.SPEC.acceptConfig(event.getConfig().getConfigData());
            DragonSurvivalCompatibility.LOG.info("Client configuration has been reloaded");
        }

        if (event.getConfig().getSpec() == ServerConfig.SPEC) {
            ServerConfig.SPEC.acceptConfig(event.getConfig().getConfigData());
            DragonSurvivalCompatibility.LOG.info("Server configuration has been reloaded");
        }
    }
}
