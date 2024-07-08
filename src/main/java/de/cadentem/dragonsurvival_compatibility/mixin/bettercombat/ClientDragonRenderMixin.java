package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import de.cadentem.dragonsurvival_compatibility.compat.bettercombat.AnimationUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientDragonRender.class, remap = false)
public abstract class ClientDragonRenderMixin {
    /** Render the Better Combat attack animation */
    @Inject(method = "thirdPersonPreRender", at = @At("HEAD"), cancellable = true, remap = false)
    private static void dragonsurvival_compatibility$modifyRender(final RenderPlayerEvent.Pre event, final CallbackInfo callback) {
        if (ClientConfig.BETTERCOMBAT.get()) {
            if (!AnimationUtils.shouldHideModel(event.getEntity())) {
                return;
            }

            if (DragonUtils.isDragon(event.getEntity())) {
                event.getRenderer().getModel().setAllVisible(false);
                Minecraft minecraft = Minecraft.getInstance();

                if (minecraft.player == event.getEntity() && minecraft.options.getCameraType().isFirstPerson()) {
                    // First person needs to hide the dragon model
                    callback.cancel();
                }
            }
        }
    }

    /** Render the tool when attacking */
    @WrapWithCondition(method = "thirdPersonPreRender", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/RenderPlayerEvent$Pre;setCanceled(Z)V"))
    private static boolean dragonsurvival_compatibility$renderTool(final RenderPlayerEvent.Pre instance, boolean isCancelled) {
        if (ClientConfig.BETTERCOMBAT.get() && AnimationUtils.shouldHideModel(instance.getEntity())) {
            return false;
        }

        return isCancelled;
    }
}
