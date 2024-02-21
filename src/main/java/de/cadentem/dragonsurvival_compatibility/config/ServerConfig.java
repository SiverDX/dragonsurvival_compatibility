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
    public static ForgeConfigSpec.BooleanValue PUFFISH_SKILLS;
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
            coldSweatAttributes.coldResistance = BUILDER.comment("Dragon Cold Resistance (Resistance against freezing damage and effects)").defineInRange("cold_resistance", coldResistanceDefault, 0, 1);
            double coldDampeningDefault = dragonType.equals(FOREST_DRAGON) ? 0.2 : 0;
            coldSweatAttributes.coldDampening = BUILDER.comment("Dragon Cold Dampening (Slows (negative values accelerate) the rate at of freezing)").defineInRange("cold_dampening", coldDampeningDefault, -1024, 1);
            coldSweatAttributes.freezingPoint = BUILDER.comment("Dragon Freezing Point (The minimum habitable world temperature)").defineInRange("freezing_point", 0d, -1024, 1024);

            // Heat
            double heatResistanceDefault = dragonType.equals(CAVE_DRAGON) ? 1.0 : dragonType.equals(FOREST_DRAGON) ? 0.3 : 0;
            coldSweatAttributes.heatResistance = BUILDER.comment("Dragon Heat Resistance (Resistance against burning damage and effects)").defineInRange("heat_resistance", heatResistanceDefault, 0, 1);
            double heatDampeningDefault = dragonType.equals(FOREST_DRAGON) ? 0.2 : 0;
            coldSweatAttributes.heatDampening = BUILDER.comment("Dragon Heat Dampening (Slows (negative values accelerate) the rate at of overheating)").defineInRange("heat_dampening", heatDampeningDefault, -1024, 1);
            coldSweatAttributes.burningPoint = BUILDER.comment("Dragon Burning Point Offset (The maximum habitable world temperature)").defineInRange("burning_point", 0d, -1024, 1024);

            // General
            coldSweatAttributes.baseTemperature = BUILDER.comment("Dragon Base Temperature (The body temperature (applied once)").defineInRange("base_temperature", 0d, -150, 150);
            coldSweatAttributes.worldTemperature = BUILDER.comment("Dragon World Temperature (The perceived world temperature)").defineInRange("world_temperature", 0d, -1024, 1024);

            COLD_SWEAT_ATTRIBUTES.put(dragonType, coldSweatAttributes);
            BUILDER.pop();
        }

        BUILDER.pop();

        UPGRADED_NETHERITE = BUILDER.comment("Enable Upgraded Netherite Compatibility").define("upgraded_netherite", true);
        PUFFISH_SKILLS = BUILDER.comment("Enable Pufferfish's Skills Compatibility").define("puffish_skills", true);
        APOTHEOSIS = BUILDER.comment("Enable Apotheosis Compatibility").define("apotheosis", true);
        FORBIDDEN_ARCANUS = BUILDER.comment("Enable Forbidden and Arcanus Compatibility").define("forbidden_arcanus", true);
        SPEC = BUILDER.build();
    }
}
