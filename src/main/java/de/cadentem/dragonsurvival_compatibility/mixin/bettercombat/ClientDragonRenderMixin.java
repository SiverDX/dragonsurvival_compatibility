package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientDragonRender.class)
public abstract class ClientDragonRenderMixin {
    /** @reason Render the Better Combat attack animation (but not the player skin) */
    @Inject(method = "thirdPersonPreRender", at = @At("HEAD"), cancellable = true, remap = false)
    private static void dragonsurvival_compatibility$modifyRender(final RenderPlayerEvent.Pre event, final CallbackInfo callback) {
        if (ClientConfig.BETTERCOMBAT.get()) {
            if (!(event.getEntity() instanceof AbstractClientPlayer player) || Utils.HIDE_MODEL_LENGTH <= 0) {
                return;
            }

            Minecraft minecraft = Minecraft.getInstance();

            if ((player == minecraft.player || minecraft.player == null) && minecraft.screen == null && minecraft.options.getCameraType().isFirstPerson() && DragonUtils.isDragon(player)) {
                event.getRenderer().getModel().setAllVisible(false);
                callback.cancel();
            }
        }
    }
}
