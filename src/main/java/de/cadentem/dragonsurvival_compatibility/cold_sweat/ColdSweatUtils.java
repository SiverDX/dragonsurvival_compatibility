package de.cadentem.dragonsurvival_compatibility.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import com.mojang.datafixers.util.Pair;
import com.momosoftworks.coldsweat.util.registries.ModAttributes;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ColdSweatUtils {
    private static final Pair<String, String> COLD_RESISTANCE = Pair.of("8e00163d-33fe-42f6-9fab-679788777061", "[Dragon] Innate Cold Resistance");
    private static final Pair<String, String> COLD_DAMPENING = Pair.of("17be7f69-5341-4291-a4f8-c4b6a5e98769", "[Dragon] Innate Cold Dampening");
    private static final Pair<String, String> HEAT_RESISTANCE = Pair.of("0ec32f49-32f7-472b-b492-e5b37d09e4d9", "[Dragon] Innate Heat Resistance");
    private static final Pair<String, String> HEAT_DAMPENING = Pair.of("7e13941d-f8f9-40e9-bffa-31117a20ca04", "[Dragon] Innate Heat Dampening");

    public static void addModifiers(final Player player, final DragonStateHandler handler) {
        ColdSweatUtils.removeModifiers(player);

        ServerConfig.COLD_SWEAT_ATTRIBUTES.forEach((key, values) -> {
            if (handler.getTypeName().equals(key)) {
                for (int i = 0; i < values.length; i++) {
                    switch (i) {
                        case 0:
                            ColdSweatUtils.addModifier(player, ModAttributes.COLD_RESISTANCE, COLD_RESISTANCE, values[i].get());
                            break;
                        case 1:
                            ColdSweatUtils.addModifier(player, ModAttributes.COLD_DAMPENING, COLD_DAMPENING, values[i].get());
                            break;
                        case 2:
                            ColdSweatUtils.addModifier(player, ModAttributes.HEAT_RESISTANCE, HEAT_RESISTANCE, values[i].get());
                            break;
                        case 3:
                            ColdSweatUtils.addModifier(player, ModAttributes.HEAT_DAMPENING, HEAT_DAMPENING, values[i].get());
                            break;
                    }
                }
            }
        });
    }

    public static void removeModifiers(final Player player) {
        removeModifier(player, ModAttributes.COLD_RESISTANCE, COLD_RESISTANCE);
        removeModifier(player, ModAttributes.COLD_DAMPENING, COLD_DAMPENING);
        removeModifier(player, ModAttributes.HEAT_RESISTANCE, HEAT_RESISTANCE);
        removeModifier(player, ModAttributes.HEAT_DAMPENING, HEAT_DAMPENING);
    }

    public static void addModifier(final Player player, final Attribute attribute, final Pair<String, String> attributeInformation, final Double value) {
        if (value == 0) {
            return;
        }

        AttributeInstance attributeInstance = player.getAttribute(attribute);

        if (attributeInstance != null) {
            attributeInstance.addPermanentModifier(new AttributeModifier(UUID.fromString(attributeInformation.getFirst()), attributeInformation.getSecond(), value, AttributeModifier.Operation.ADDITION));
        }
    }

    public static void removeModifier(final Player player, final Attribute attribute, final Pair<String, String> attributeInformation) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);

        if (attributeInstance != null) {
            attributeInstance.removePermanentModifier(UUID.fromString(attributeInformation.getFirst()));
        }
    }
}
