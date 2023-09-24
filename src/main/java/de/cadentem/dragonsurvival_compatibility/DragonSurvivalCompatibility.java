package de.cadentem.dragonsurvival_compatibility;

import com.mojang.logging.LogUtils;
import de.cadentem.dragonsurvival_compatibility.cold_sweat.ColdSweatEventHandler;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import de.cadentem.dragonsurvival_compatibility.upgradednetherite.UpgradedNetheriteEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DragonSurvivalCompatibility.MODID)
public class DragonSurvivalCompatibility {
    public static final String MODID = "dragonsurvival_compatibility";
    public static final Logger LOG = LogUtils.getLogger();

    public DragonSurvivalCompatibility() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().register(this);

        if (ModList.get().isLoaded("upgradednetherite")) {
            MinecraftForge.EVENT_BUS.register(new UpgradedNetheriteEventHandler());
        }

        if (ModList.get().isLoaded("cold_sweat")) {
//            MinecraftForge.EVENT_BUS.register(ColdSweatEventHandler.class);
        }
    }
    @SubscribeEvent
    public void reloadConfiguration(final ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == ServerConfig.SPEC) {
            ServerConfig.SPEC.acceptConfig(event.getConfig().getConfigData());

            DragonSurvivalCompatibility.LOG.info("Config was reloaded");
        }
    }
}
