package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientDragonRender.class, remap = false)
public abstract class ClientDragonRenderMixin {
    /** @reason Render the Better Combat attack animation (but not the player skin) */
    @Inject(method = "thirdPersonPreRender", at = @At("HEAD"), cancellable = true, remap = false)
    private static void dragonsurvival_compatibility$modifyRender(final RenderPlayerEvent.Pre event, final CallbackInfo callback) {
        if (ClientConfig.BETTERCOMBAT.get()) {
            if (!(event.getEntity() instanceof AbstractClientPlayer player) || Utils.HIDE_MODEL_LENGTH <= 0) {
                return;
            }

            Minecraft minecraft = Minecraft.getInstance();

            if ((player == minecraft.player || minecraft.player == null) && DragonUtils.isDragon(player)) {
                event.getRenderer().getModel().setAllVisible(false);

                if (minecraft.options.getCameraType().isFirstPerson()) {
                    callback.cancel();
                }
            }
        }
    }

    /** Render the tool when attacking */
    @WrapWithCondition(method = "thirdPersonPreRender", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/RenderPlayerEvent$Pre;setCanceled(Z)V"))
    private static boolean test(final RenderPlayerEvent.Pre instance, boolean isCancelled) {
        if ((instance.getEntity() == Minecraft.getInstance().player || Minecraft.getInstance().player == null) && ClientConfig.BETTERCOMBAT.get() && !Minecraft.getInstance().options.getCameraType().isFirstPerson() && Utils.HIDE_MODEL_LENGTH > 0) {
            return false;
        }

        return isCancelled;
    }
}
