package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Activate or Disable
    public static ForgeConfigSpec.BooleanValue ENABLE_BETTERCOMBAT;
    public static ForgeConfigSpec.BooleanValue ENABLE_JADE;
    public static ForgeConfigSpec.BooleanValue ENABLE_WTHITHARVESTABILITY;
    public static ForgeConfigSpec.BooleanValue ENABLE_RAISED;
    public static ForgeConfigSpec.BooleanValue ENABLE_COLD_SWEAT;

    // Extra Configuration
    public static ForgeConfigSpec.BooleanValue PREFER_HARVEST_SPEED;

    static {
        BUILDER.push("Activate or Disable");
        ENABLE_BETTERCOMBAT = BUILDER.comment("Enable Better Combat compatibility").define("enable_bettercombat", true);
        ENABLE_JADE = BUILDER.comment("Enable Jade Compatibility").define("enable_jade", true);
        ENABLE_WTHITHARVESTABILITY = BUILDER.comment("Enable WTHIT Harvestability Compatibility").define("enable_wthitharvestability", true);
        ENABLE_RAISED = BUILDER.comment("Enable Raised Compatibility").define("raised", true);
        ENABLE_COLD_SWEAT = BUILDER.comment("Enable Cold Sweat Compatibility (render changes)").define("enable_cold_sweat", true);
        BUILDER.pop();

        BUILDER.push("Extra Configuration");
        PREFER_HARVEST_SPEED = BUILDER.comment("[Apotheosis] Whether to prefer the harvest speed (incl. the Omnetic affix) or the Radial affix effect for tools in the claw inventory").define("prefer_harvest_speed", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
