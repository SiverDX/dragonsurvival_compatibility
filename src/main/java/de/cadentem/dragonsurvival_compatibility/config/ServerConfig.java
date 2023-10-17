package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue COLD_SWEAT;
    public static ForgeConfigSpec.BooleanValue UPGRADED_NETHERITE;
    public static ForgeConfigSpec.BooleanValue PUFFISH_SKILLS;
    public static ForgeConfigSpec.BooleanValue APOTHEOSIS;
    public static ForgeConfigSpec.BooleanValue FORBIDDEN_ARCANUS;

    static {
        COLD_SWEAT = BUILDER.comment("Enable Cold Sweat Compatibility").define("cold_sweat", true);
        UPGRADED_NETHERITE = BUILDER.comment("Enable Upgraded Netherite Compatibility").define("upgraded_netherite", true);
        PUFFISH_SKILLS = BUILDER.comment("Enable Pufferfish's Skills Compatibility").define("puffish_skills", true);
        APOTHEOSIS = BUILDER.comment("Enable Apotheosis Compatibility").define("apotheosis", true);
        FORBIDDEN_ARCANUS = BUILDER.comment("Enable Forbidden and Arcanus Compatibility").define("forbidden_arcanus", true);
        SPEC = BUILDER.build();
    }
}
