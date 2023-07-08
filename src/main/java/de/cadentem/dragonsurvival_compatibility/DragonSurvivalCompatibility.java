package de.cadentem.dragonsurvival_compatibility;

import com.mojang.logging.LogUtils;
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
    public static final Logger LOGGER = LogUtils.getLogger();

    public DragonSurvivalCompatibility() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().register(this);

        if (ModList.get().isLoaded("upgradednetherite")) {
            MinecraftForge.EVENT_BUS.register(new UpgradedNetheriteEventHandler());
        }
    }
    @SubscribeEvent
    public void test(final ModConfigEvent.Reloading event) {
        // FIXME :: Configured does not post an event for server config
        if (event.getConfig().getSpec() == ServerConfig.SPEC) {
            ServerConfig.ENABLE_UPGRADED_NETHERITE.clearCache();
            ServerConfig.ENABLE_COLD_SWEAT.clearCache();
        }
    }
}
