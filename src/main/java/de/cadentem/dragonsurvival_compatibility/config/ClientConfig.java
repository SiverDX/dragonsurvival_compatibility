package de.cadentem.dragonsurvival_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_BETTERCOMBAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_JADE;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_WTHITHARVESTABILITY;

    static {
        ENABLE_BETTERCOMBAT = BUILDER.comment("Enable Better Combat compatibility").define("enable_bettercombat", true);
        ENABLE_JADE = BUILDER.comment("Enable Jade compatibility").define("enable_jade", true);
        ENABLE_WTHITHARVESTABILITY = BUILDER.comment("Enable WTHIT Harvestability compatibility").define("enable_wthitharvestability", true);
        SPEC = BUILDER.build();
    }
}
