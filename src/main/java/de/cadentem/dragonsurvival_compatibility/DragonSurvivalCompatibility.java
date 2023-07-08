package de.cadentem.dragonsurvival_compatibility;

import com.mojang.logging.LogUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import de.cadentem.dragonsurvival_compatibility.upgradednetherite.UpgradedNetheriteEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(DragonSurvivalCompatibility.MODID)
public class DragonSurvivalCompatibility {
    public static final String MODID = "dragonsurvival_compatibility";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DragonSurvivalCompatibility() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "dragonsurvival_compatibility-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC, "dragonsurvival_compatibility-server.toml");

        if (ModList.get().isLoaded("upgradednetherite")) {
            MinecraftForge.EVENT_BUS.register(new UpgradedNetheriteEventHandler());
        }
    }
}
