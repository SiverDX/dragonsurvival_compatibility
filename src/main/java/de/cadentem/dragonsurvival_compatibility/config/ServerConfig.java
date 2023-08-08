package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue ENABLE_COLD_SWEAT;
    public static ForgeConfigSpec.BooleanValue ENABLE_UPGRADED_NETHERITE;
    public static ForgeConfigSpec.BooleanValue ENABLE_PUFFISH_SKILLS;

    static {
        ENABLE_COLD_SWEAT = BUILDER.comment("Enable Cold Sweat Compatibility").define("enable_cold_sweat", true);
        ENABLE_UPGRADED_NETHERITE = BUILDER.comment("Enable Upgraded Netherite Compatibility").define("enable_upgraded_netherite", true);
        ENABLE_PUFFISH_SKILLS = BUILDER.comment("Enable Pufferfish's Skills Compatibility").define("enable_puffish_skills", true);
        SPEC = BUILDER.build();
    }
}
