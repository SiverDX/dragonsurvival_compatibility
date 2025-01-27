package de.cadentem.dragonsurvival_compatibility.compat.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.momosoftworks.coldsweat.util.registries.ModAttributes;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ColdSweatEventHandler {
    public static void handleAttributes(final PlayerEvent.PlayerLoggedInEvent event) {
        if (!ServerConfig.COLD_SWEAT.get()) {
            return;
        }

        ColdSweatUtils.addModifiers(event.getEntity(), DragonUtils.getHandler(event.getEntity()));
    }

    // FIXME :: remove once they fix their mod
    public static void attachAttributes(final EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.COLD_DAMPENING);
        event.add(EntityType.PLAYER, ModAttributes.HEAT_DAMPENING);
        event.add(EntityType.PLAYER, ModAttributes.COLD_RESISTANCE);
        event.add(EntityType.PLAYER, ModAttributes.HEAT_RESISTANCE);
        event.add(EntityType.PLAYER, ModAttributes.BURNING_POINT);
        event.add(EntityType.PLAYER, ModAttributes.FREEZING_POINT);
        event.add(EntityType.PLAYER, ModAttributes.BASE_BODY_TEMPERATURE);
        event.add(EntityType.PLAYER, ModAttributes.WORLD_TEMPERATURE);
    }
}
