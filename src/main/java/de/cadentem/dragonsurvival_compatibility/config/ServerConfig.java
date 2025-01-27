package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static Map<String, ColdSweatAttributes> COLD_SWEAT_ATTRIBUTES = new HashMap<>();

    public static ForgeConfigSpec.BooleanValue COLD_SWEAT;
    public static ForgeConfigSpec.BooleanValue UPGRADED_NETHERITE;
    public static ForgeConfigSpec.BooleanValue APOTHEOSIS;
    public static ForgeConfigSpec.BooleanValue FORBIDDEN_ARCANUS;

    private final static String CAVE_DRAGON = "cave";
    private final static String SEA_DRAGON = "sea";
    private final static String FOREST_DRAGON = "forest";

    static {
        String[] dragonTypes = {CAVE_DRAGON, SEA_DRAGON, FOREST_DRAGON};

        BUILDER.push("Cold Sweat");
        COLD_SWEAT = BUILDER.comment("Enable Cold Sweat Compatibility").define("cold_sweat", true);

        for (String dragonType : dragonTypes) {
            BUILDER.push(dragonType.toUpperCase());
            ColdSweatAttributes coldSweatAttributes = new ColdSweatAttributes();

            // Cold
            double coldResistanceDefault = dragonType.equals(SEA_DRAGON) ? 1.0 : dragonType.equals(FOREST_DRAGON) ? 0.3 : 0;
            coldSweatAttributes.coldResistance = BUILDER.comment("Controls the entity's resistance to incoming freezing damage, including that from non-temperature-related sources like powder snow.\nRepresents the percent of incoming damage to be blocked.").defineInRange("cold_resistance", coldResistanceDefault, 0, 1);
            double coldDampeningDefault = dragonType.equals(FOREST_DRAGON) ? 0.2 : 0;
            coldSweatAttributes.coldDampening = BUILDER.comment("Controls the entity's rate of freezing. Higher values decrease freezing speed, and negative values increase the speed.").defineInRange("cold_dampening", coldDampeningDefault, -1024, 1);
            double freezingPointDefault = dragonType.equals(SEA_DRAGON) ? -1024 : 0;
            coldSweatAttributes.freezingPoint = BUILDER.comment("Controls the temperature at which the entity begins to freeze, in MC units.").defineInRange("freezing_point", freezingPointDefault, -1024, 1024);

            // Heat
            double heatResistanceDefault = dragonType.equals(CAVE_DRAGON) ? 1.0 : dragonType.equals(FOREST_DRAGON) ? 0.3 : 0;
            coldSweatAttributes.heatResistance = BUILDER.comment("Controls the entity's resistance to incoming overheating damage.\nRepresents the percent of incoming damage to be blocked.").defineInRange("heat_resistance", heatResistanceDefault, 0, 1);
            double heatDampeningDefault = dragonType.equals(FOREST_DRAGON) ? 0.2 : 0;
            coldSweatAttributes.heatDampening = BUILDER.comment("Controls the entity's rate of overheating. Higher values decrease overheating speed, and negative values increase the speed.").defineInRange("heat_dampening", heatDampeningDefault, -1024, 1);
            double burningPointDefault = dragonType.equals(CAVE_DRAGON) ? 1024 : 0;
            coldSweatAttributes.burningPoint = BUILDER.comment("Controls the temperature at which the entity begins to overheat, in MC units.").defineInRange("burning_point", burningPointDefault, -1024, 1024);

            // General
            coldSweatAttributes.baseTemperature = BUILDER.comment("Controls the entity's base body temperature, (AKA an offset to the entity's body temperature).").defineInRange("base_temperature", 0d, -150, 150);
            coldSweatAttributes.worldTemperature = BUILDER.comment("Controls the entity's world temperature.").defineInRange("world_temperature", 0d, -1024, 1024);

            COLD_SWEAT_ATTRIBUTES.put(dragonType, coldSweatAttributes);
            BUILDER.pop();
        }

        BUILDER.pop();

        UPGRADED_NETHERITE = BUILDER.comment("Enable Upgraded Netherite compatibility").define("upgraded_netherite", true);
        APOTHEOSIS = BUILDER.comment("Enable Apotheosis compatibility").define("apotheosis", true);
        FORBIDDEN_ARCANUS = BUILDER.comment("Enable Forbidden and Arcanus compatibility").define("forbidden_arcanus", true);
        SPEC = BUILDER.build();
    }
}
