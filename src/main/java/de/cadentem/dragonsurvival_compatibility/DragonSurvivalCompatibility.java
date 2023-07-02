package de.cadentem.dragonsurvival_compatibility;

import com.mojang.logging.LogUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DragonSurvivalCompatibility.MODID)
public class DragonSurvivalCompatibility {
    public static final String MODID = "dragonsurvival_compatibility";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DragonSurvivalCompatibility() {
//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "dragonsurvival_compatibility-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC, "dragonsurvival_compatibility-server.toml");

//        modEventBus.addListener(this::reloadConfig);
    }

//    public void reloadConfig(final ModConfigEvent.Reloading event) {
//        // Only gets called for client config
//        ModConfig config = event.getConfig();
//
//        if (config.getSpec() == ServerConfig.SPEC) {
//            ServerConfig.ENABLE_COLD_SWEAT.clearCache();
//        }
//    }
}
