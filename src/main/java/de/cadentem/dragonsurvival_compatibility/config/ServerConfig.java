package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_COLD_SWEAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_UPGRADED_NETHERITE;

    static {
        BUILDER.comment("These changes require a server restart");
        ENABLE_COLD_SWEAT = BUILDER.comment("Enable Cold Sweat compatibility").define("enable_cold_sweat", true);
        ENABLE_UPGRADED_NETHERITE = BUILDER.comment("Enable Upgraded Netherite Compatibility").define("enable_upgraded_netherite", true);
        SPEC = BUILDER.build();
    }
}
