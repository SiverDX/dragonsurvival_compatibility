package de.cadentem.dragonsurvival_compatibility;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DragonSurvivalCompatibility.MODID)
public class DragonSurvivalCompatibility {
    public static final String MODID = "dragonsurvival_compatibility";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final boolean IS_BETTERCOMBAT_LOADED = ModList.get().isLoaded("bettercombat");

    public DragonSurvivalCompatibility() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) { /* Nothing to do */ }

    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) { /* Nothing to do */ }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) { /* Nothing to do */ }
    }
}
