package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.client.handlers.magic.ClientMagicHUDHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** XP bar */
@Mixin(ClientMagicHUDHandler.class)
public abstract class MixinClientMagicHUDHandler {
    @Inject(method = "renderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I", shift = At.Shift.BEFORE, ordinal = 0))
    private static void dragonsurvival_compatibility$renderExperienceBar1(final ForgeGui gui, final PoseStack poseStack, int width, final CallbackInfoReturnable<Boolean> callback) {
        if (ClientConfig.COLD_SWEAT.get() && ConfigSettings.CUSTOM_HOTBAR_LAYOUT.get()) {
            poseStack.translate(0.0D, 4.0D, 0.0D);
        }
    }

    @Inject(method = "renderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I", shift = At.Shift.AFTER, ordinal = 4))
    private static void dragonsurvival_compatibility$renderExperienceBar2(final ForgeGui gui, final PoseStack poseStack, int width, final CallbackInfoReturnable<Boolean> callback) {
        if (ClientConfig.COLD_SWEAT.get() && ConfigSettings.CUSTOM_HOTBAR_LAYOUT.get()) {
            poseStack.translate(0.0D, -4.0D, 0.0D);
        }
    }
}
