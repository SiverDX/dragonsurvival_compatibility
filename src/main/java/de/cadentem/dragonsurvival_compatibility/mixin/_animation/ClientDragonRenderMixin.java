package de.cadentem.dragonsurvival_compatibility.mixin._animation;

import by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import de.cadentem.dragonsurvival_compatibility.compat.bettercombat.AnimationUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientDragonRender.class, remap = false)
public abstract class ClientDragonRenderMixin {
    /** Prevent the head from blocking the view */
    @Inject(method = "thirdPersonPreRender", at = @At("HEAD"), cancellable = true, remap = false)
    private static void dragonsurvival_compatibility$modifyRender(final RenderPlayerEvent.Pre event, final CallbackInfo callback) {
        if (!DragonUtils.isDragon(event.getEntity())) {
            return;
        }

        boolean isAttacking = false;

        if (AnimationUtils.isAttacking(event.getEntity(), AnimationUtils.Type.BETTERCOMBAT)) {
            // Only make the parts invisible so that the weapon is still rendered
            event.getRenderer().getModel().setAllVisible(false);
            isAttacking = true;
        } else if (AnimationUtils.isAttacking(event.getEntity(), AnimationUtils.Type.IRONS_SPELLBOOKS)) {
            // Prevent the hands from being rendered
            event.setCanceled(true);
            isAttacking = true;
        }

        if (!isAttacking) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == event.getEntity() && minecraft.options.getCameraType().isFirstPerson()) {
            // First person needs to hide the dragon model
            callback.cancel();
        }
    }

    /** Render the tool when attacking */
    @WrapWithCondition(method = "thirdPersonPreRender", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/RenderPlayerEvent$Pre;setCanceled(Z)V"))
    private static boolean dragonsurvival_compatibility$renderTool(final RenderPlayerEvent.Pre instance, boolean isCancelled) {
        if (AnimationUtils.isAttacking(instance.getEntity(), AnimationUtils.Type.BETTERCOMBAT)) {
            return false;
        }

        return isCancelled;
    }
}
