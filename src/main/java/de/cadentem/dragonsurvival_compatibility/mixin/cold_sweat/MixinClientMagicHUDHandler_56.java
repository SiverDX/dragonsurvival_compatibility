package de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat;

import by.dragonsurvivalteam.dragonsurvival.client.handlers.magic.ClientMagicHUDHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import dev.momostudios.coldsweat.config.ClientSettingsConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientMagicHUDHandler.class)
public class MixinClientMagicHUDHandler_56 {
    @Inject(method = "lambda$cancelExpBar$0", at = @At(value = "INVOKE", target = "Lby/dragonsurvivalteam/dragonsurvival/client/handlers/magic/ClientMagicHUDHandler;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.AFTER, ordinal = 1))
    private static void renderExperienceBar1(final Player player, final PoseStack poseStack, int x, final DragonStateHandler handler, final CallbackInfo callback) {
        if (ClientConfig.ENABLE_COLD_SWEAT.get()) {
            // Render XP bar
            if (ClientSettingsConfig.getInstance().customHotbarEnabled()) {
                poseStack.translate(0.0D, 4.0D, 0.0D);
            }
        }
    }

    @Inject(method = "lambda$cancelExpBar$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I", shift = At.Shift.AFTER, ordinal = 4))
    private static void renderExperienceBar2(final Player player, final PoseStack poseStack, int x, final DragonStateHandler handler, final CallbackInfo callback) {
        if (ClientConfig.ENABLE_COLD_SWEAT.get()) {
            // Render XP bar
            if (ClientSettingsConfig.getInstance().customHotbarEnabled()) {
                poseStack.translate(0.0D, -4.0D, 0.0D);
            }
        }
    }
}
