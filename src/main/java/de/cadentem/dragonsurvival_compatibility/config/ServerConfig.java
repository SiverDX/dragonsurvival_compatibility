package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_COLD_SWEAT;

    static {
        ENABLE_COLD_SWEAT = BUILDER.comment("Enable Cold Sweat compatibility (Restart required)").define("enable_cold_sweat", true);
        SPEC = BUILDER.build();
    }
}
