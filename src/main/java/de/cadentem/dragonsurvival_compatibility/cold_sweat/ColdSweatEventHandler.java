package de.cadentem.dragonsurvival_compatibility.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ColdSweatEventHandler {
    public static void handleAttributes(final PlayerEvent.PlayerLoggedInEvent event) {
        if (!ServerConfig.COLD_SWEAT.get()) {
            return;
        }

        ColdSweatUtils.addModifiers(event.getEntity(), DragonUtils.getHandler(event.getEntity()));
    }
}
