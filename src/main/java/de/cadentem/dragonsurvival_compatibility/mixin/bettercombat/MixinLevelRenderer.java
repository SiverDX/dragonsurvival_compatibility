package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib3.core.processor.IBone;

import static de.cadentem.dragonsurvival_compatibility.DragonSurvivalCompatibility.IS_BETTERCOMBAT_LOADED;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    /** Avoid rendering the floating weapon */
    @Inject(method = "renderEntity", at = @At(value = "HEAD"), cancellable = true)
    public void skipRenderEntity(final Entity entity, double camX, double camY, double camZ, float partialTick, final PoseStack poseStack, final MultiBufferSource bufferSource, final CallbackInfo callback) {
        if (IS_BETTERCOMBAT_LOADED && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            final IBone neckAndHead = ClientDragonRender.dragonModel.getAnimationProcessor().getBone("Neck");

            if (neckAndHead != null && neckAndHead.isHidden()) {
                callback.cancel();
            }
        }
    }
}
