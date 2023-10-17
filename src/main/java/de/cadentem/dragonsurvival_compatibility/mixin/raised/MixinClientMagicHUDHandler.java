package de.cadentem.dragonsurvival_compatibility.mixin.raised;

import by.dragonsurvivalteam.dragonsurvival.client.handlers.magic.ClientMagicHUDHandler;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import dev.yurisuika.raised.Raised;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ClientMagicHUDHandler.class)
public class MixinClientMagicHUDHandler {
    @ModifyArg(method = "lambda$cancelExpBar$0", at = @At(value = "INVOKE", target = "Lby/dragonsurvivalteam/dragonsurvival/client/handlers/magic/ClientMagicHUDHandler;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V"), index = 2)
    private static int modifyExperienceBar(int value) {
        if (ClientConfig.RAISED.get()) {
            return value - Raised.getHud();
        }

        return value;
    }

    @ModifyArg(method = "lambda$cancelExpBar$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I"), index = 3)
    private static float modifyXpText(float value) {
        if (ClientConfig.RAISED.get()) {
            return value - (float) Raised.getHud();
        }

        return value;
    }
}
