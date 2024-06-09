package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Activate or Disable
    public static ForgeConfigSpec.BooleanValue BETTERCOMBAT;
    public static ForgeConfigSpec.BooleanValue JADE;
    public static ForgeConfigSpec.BooleanValue WTHIT;
    public static ForgeConfigSpec.BooleanValue COLD_SWEAT;

    static {
        BUILDER.push("Activate or Disable");
        BETTERCOMBAT = BUILDER.comment("Enable Better Combat compatibility").define("bettercombat", true);
        JADE = BUILDER.comment("Enable Jade compatibility").define("enable_jade", true);
        WTHIT = BUILDER.comment("Enable WTHIT compatibility").define("wthit", true);
        COLD_SWEAT = BUILDER.comment("Enable Cold Sweat compatibility (render changes)").define("cold_sweat", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
