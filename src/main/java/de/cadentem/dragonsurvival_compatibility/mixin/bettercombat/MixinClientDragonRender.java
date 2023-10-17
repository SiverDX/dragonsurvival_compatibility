package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender;
import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientDragonRender.class)
public abstract class MixinClientDragonRender {
    /** @reason Render the Better Combat attack animation (but not the player skin) */
    @Inject(method = "thirdPersonPreRender", at = @At("HEAD"), cancellable = true, remap = false)
    private static void modifyRender(final RenderPlayerEvent.Pre renderPlayerEvent, final CallbackInfo callback) {
        if (ClientConfig.BETTERCOMBAT.get()) {
            if (!(renderPlayerEvent.getEntity() instanceof AbstractClientPlayer player)) {
                return;
            }

            Minecraft minecraft = Minecraft.getInstance();
            DragonStateHandler cap = DragonUtils.getHandler(player);

            if ((player == minecraft.player || minecraft.player == null) && minecraft.screen == null && minecraft.options.getCameraType().isFirstPerson() && cap.isDragon()) {
                renderPlayerEvent.getRenderer().getModel().setAllVisible(false);
                callback.cancel();
            }
        }
    }
}
