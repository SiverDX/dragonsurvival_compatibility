package de.cadentem.dragonsurvival_compatibility.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import com.momosoftworks.coldsweat.util.registries.ModAttributes;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ColdSweatUtils {
    private static final String COLD_RESISTANCE = "8e00163d-33fe-42f6-9fab-679788777061";
    private static final String COLD_DAMPENING = "17be7f69-5341-4291-a4f8-c4b6a5e98769";
    private static final String FREEZING_POINT = "b10f3fae-de10-4255-bfed-6171149aa437";

    private static final String HEAT_RESISTANCE = "0ec32f49-32f7-472b-b492-e5b37d09e4d9";
    private static final String HEAT_DAMPENING = "7e13941d-f8f9-40e9-bffa-31117a20ca04";
    private static final String BURNING_POINT = "08d592b7-5e5d-4be4-8ac9-fd1b4bc8f37e";

    private static final String BASE_TEMPERATURE = "3e252bf8-d809-47b2-bd67-0b1044fe0d09";
    private static final String WORLD_TEMPERATURE = "36852aa7-c941-4482-9c0c-f42d7f0ae270";

    public static void addModifiers(final Player player, final DragonStateHandler handler) {
        ColdSweatUtils.removeModifiers(player);

        ServerConfig.COLD_SWEAT_ATTRIBUTES.forEach((key, value) -> {
            if (handler.getTypeName().equals(key)) {
                ColdSweatUtils.addModifier(player, ModAttributes.COLD_RESISTANCE, COLD_RESISTANCE, "[Dragon] Cold Resistance", value.coldResistance.get());
                ColdSweatUtils.addModifier(player, ModAttributes.COLD_DAMPENING, COLD_DAMPENING, "[Dragon] Cold Dampening", value.coldDampening.get());
                ColdSweatUtils.addModifier(player, ModAttributes.FREEZING_POINT, FREEZING_POINT, "[Dragon] Freezing Point", value.freezingPoint.get());

                ColdSweatUtils.addModifier(player, ModAttributes.HEAT_RESISTANCE, HEAT_RESISTANCE, "[Dragon] Heat Resistance", value.heatResistance.get());
                ColdSweatUtils.addModifier(player, ModAttributes.HEAT_DAMPENING, HEAT_DAMPENING, "[Dragon] Heat Dampening", value.heatDampening.get());
                ColdSweatUtils.addModifier(player, ModAttributes.BURNING_POINT, BURNING_POINT, "[Dragon] Burning Point", value.burningPoint.get());

                ColdSweatUtils.addModifier(player, ModAttributes.BASE_BODY_TEMPERATURE, BASE_TEMPERATURE, "[Dragon] Base Temperature", value.baseTemperature.get());
                ColdSweatUtils.addModifier(player, ModAttributes.WORLD_TEMPERATURE, WORLD_TEMPERATURE, "[Dragon] World Temperature", value.worldTemperature.get());
            }
        });
    }

    public static void removeModifiers(final Player player) {
        removeModifier(player, ModAttributes.COLD_RESISTANCE, COLD_RESISTANCE);
        removeModifier(player, ModAttributes.COLD_DAMPENING, COLD_DAMPENING);
        removeModifier(player, ModAttributes.FREEZING_POINT, FREEZING_POINT);

        removeModifier(player, ModAttributes.HEAT_RESISTANCE, HEAT_RESISTANCE);
        removeModifier(player, ModAttributes.HEAT_DAMPENING, HEAT_DAMPENING);
        removeModifier(player, ModAttributes.BURNING_POINT, BURNING_POINT);

        removeModifier(player, ModAttributes.BASE_BODY_TEMPERATURE, BASE_TEMPERATURE);
        removeModifier(player, ModAttributes.WORLD_TEMPERATURE, WORLD_TEMPERATURE);
    }

    public static void addModifier(final Player player, final Attribute attribute, final String attributeUUID, final String attributeDescription, final Double value) {
        if (value == 0) {
            return;
        }

        AttributeInstance attributeInstance = player.getAttribute(attribute);

        if (attributeInstance != null) {
            AttributeModifier modifier = new AttributeModifier(UUID.fromString(attributeUUID), attributeDescription, value, AttributeModifier.Operation.ADDITION);

            // For safety
            if (attributeInstance.hasModifier(modifier)) {
                attributeInstance.removeModifier(modifier);
            }

            attributeInstance.setBaseValue(0);
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    public static void removeModifier(final Player player, final Attribute attribute, final String attributeUUID) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);

        if (attributeInstance != null) {
            attributeInstance.removePermanentModifier(UUID.fromString(attributeUUID));
        }
    }
}
