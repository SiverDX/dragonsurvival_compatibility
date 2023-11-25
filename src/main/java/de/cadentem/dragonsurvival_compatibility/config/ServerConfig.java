package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static Map<String, ForgeConfigSpec.DoubleValue[]> COLD_SWEAT_ATTRIBUTES = new HashMap<>();

    public static ForgeConfigSpec.BooleanValue COLD_SWEAT;
    public static ForgeConfigSpec.BooleanValue UPGRADED_NETHERITE;
    public static ForgeConfigSpec.BooleanValue PUFFISH_SKILLS;
    public static ForgeConfigSpec.BooleanValue APOTHEOSIS;
    public static ForgeConfigSpec.BooleanValue FORBIDDEN_ARCANUS;

    static {
        String[] dragonTypes = {"cave", "sea", "forest"};

        BUILDER.push("Cold Sweat");
        COLD_SWEAT = BUILDER.comment("Enable Cold Sweat Compatibility").define("cold_sweat", false);

        for (String dragonType : dragonTypes) {
            BUILDER.push(dragonType.toUpperCase());
            ForgeConfigSpec.DoubleValue coldResistance = BUILDER.comment("Innate cold resistance").defineInRange("cold_resistance", dragonType.equals("sea") ? 1.0 : dragonType.equals("forest") ? 0.2 : 0, 0, 1);
            ForgeConfigSpec.DoubleValue coldDampening = BUILDER.comment("Innate cold dampening").defineInRange("cold_dampening", dragonType.equals("forest") ? 0.2 : 0, -1024, 1);
            ForgeConfigSpec.DoubleValue heatResistance = BUILDER.comment("Innate heat resistance").defineInRange("heat_resistance", dragonType.equals("cave") ? 1.0 : dragonType.equals("forest") ? 0.2 : 0, 0, 1);
            ForgeConfigSpec.DoubleValue heatDampening = BUILDER.comment("Innate heat dampening").defineInRange("heat_dampening", dragonType.equals("forest") ? 0.2 : 0, -1024, 1);
            COLD_SWEAT_ATTRIBUTES.put(dragonType, new ForgeConfigSpec.DoubleValue[]{coldResistance, coldDampening, heatResistance, heatDampening});
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
