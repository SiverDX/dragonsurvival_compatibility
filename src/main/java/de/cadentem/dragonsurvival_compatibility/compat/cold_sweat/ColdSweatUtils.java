package de.cadentem.dragonsurvival_compatibility.compat.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import com.momosoftworks.coldsweat.util.registries.ModAttributes;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ColdSweatUtils {
    private static final UUID MODIFIER_UUID = UUID.fromString("8e00163d-33fe-42f6-9fab-679788777061");

    public static void addModifiers(final Player player, final DragonStateHandler handler) {
        ColdSweatUtils.removeModifiers(player);

        ServerConfig.COLD_SWEAT_ATTRIBUTES.forEach((key, value) -> {
            if (handler.getTypeName().equals(key)) {
                ColdSweatUtils.addModifier(player, ModAttributes.COLD_RESISTANCE, "[Dragon] Cold Resistance", value.coldResistance.get());
                ColdSweatUtils.addModifier(player, ModAttributes.COLD_DAMPENING, "[Dragon] Cold Dampening", value.coldDampening.get());
                ColdSweatUtils.addModifier(player, ModAttributes.FREEZING_POINT, "[Dragon] Freezing Point", value.freezingPoint.get());

                ColdSweatUtils.addModifier(player, ModAttributes.HEAT_RESISTANCE, "[Dragon] Heat Resistance", value.heatResistance.get());
                ColdSweatUtils.addModifier(player, ModAttributes.HEAT_DAMPENING, "[Dragon] Heat Dampening", value.heatDampening.get());
                ColdSweatUtils.addModifier(player, ModAttributes.BURNING_POINT, "[Dragon] Burning Point", value.burningPoint.get());

                ColdSweatUtils.addModifier(player, ModAttributes.BASE_BODY_TEMPERATURE, "[Dragon] Base Temperature", value.baseTemperature.get());
                ColdSweatUtils.addModifier(player, ModAttributes.WORLD_TEMPERATURE, "[Dragon] World Temperature", value.worldTemperature.get());
            }
        });
    }

    public static void removeModifiers(final Player player) {
        removeModifier(player, ModAttributes.COLD_RESISTANCE);
        removeModifier(player, ModAttributes.COLD_DAMPENING);
        removeModifier(player, ModAttributes.FREEZING_POINT);

        removeModifier(player, ModAttributes.HEAT_RESISTANCE);
        removeModifier(player, ModAttributes.HEAT_DAMPENING);
        removeModifier(player, ModAttributes.BURNING_POINT);

        removeModifier(player, ModAttributes.BASE_BODY_TEMPERATURE);
        removeModifier(player, ModAttributes.WORLD_TEMPERATURE);
    }

    public static void addModifier(final Player player, final Attribute attribute, final String attributeDescription, final Double value) {
        if (value == 0) {
            return;
        }

        AttributeInstance attributeInstance = player.getAttribute(attribute);

        if (attributeInstance != null) {
            AttributeModifier modifier = new AttributeModifier(MODIFIER_UUID, attributeDescription, value, AttributeModifier.Operation.ADDITION);

            // For safety
            if (attributeInstance.hasModifier(modifier)) {
                attributeInstance.removeModifier(modifier);
            }

            attributeInstance.setBaseValue(0);
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    public static void removeModifier(final Player player, final Attribute attribute) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);

        if (attributeInstance != null) {
            attributeInstance.removePermanentModifier(MODIFIER_UUID);
        }
    }
}
