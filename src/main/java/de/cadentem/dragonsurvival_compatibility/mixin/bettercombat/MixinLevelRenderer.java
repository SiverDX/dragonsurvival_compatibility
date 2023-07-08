package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** @reason Avoid rendering the floating weapon */
@Mixin(value = LevelRenderer.class, priority = 950) // Run before Dragon Survival
public abstract class MixinLevelRenderer {
    /** Used to "cancel" the Dragon Survival Mixin */
    @Unique private boolean dragonsurvival_compatibility$renderInFirstPerson;

    @Inject(method = "renderLevel", at = @At(value = "HEAD"))
    public void tes2t(final PoseStack poseStack, float partialTick, long finishNanoTime, boolean renderBlockOutline, final Camera camera, final GameRenderer gameRenderer, final LightTexture lightTexture, final Matrix4f projectionMatrix, final CallbackInfo ci) {
        if (ClientConfig.ENABLE_BETTERCOMBAT.get()) {
            if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                dragonsurvival_compatibility$renderInFirstPerson = ClientDragonRender.renderInFirstPerson;
                ClientDragonRender.renderInFirstPerson = false;
            }
        }
    }

    @Inject(method = "renderLevel", at = @At(value = "RETURN"))
    public void test2(final PoseStack poseStack, float partialTick, long finishNanoTime, boolean renderBlockOutline, final Camera camera, final GameRenderer gameRenderer, final LightTexture lightTexture, final Matrix4f projectionMatrix, final CallbackInfo ci) {
        if (ClientConfig.ENABLE_BETTERCOMBAT.get()) {
            if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                ClientDragonRender.renderInFirstPerson = dragonsurvival_compatibility$renderInFirstPerson;
            }
        }
    }
}
