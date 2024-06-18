package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Avoid rendering the floating weapon */
@Mixin(value = LevelRenderer.class, priority = 1500)
public abstract class LevelRendererMixin {
    @TargetHandler(mixin = "by.dragonsurvivalteam.dragonsurvival.mixins.MixinWorldRenderer", name = "render")
    @ModifyExpressionValue(method = "@MixinSquared:Handler", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, ordinal = 0))
    private Boolean dragonsurvival_compatibility$hideBodyPart(final Boolean renderInFirstPerson) {
        if (ClientConfig.BETTERCOMBAT.get() && Minecraft.getInstance().options.getCameraType().isFirstPerson() && Utils.HIDE_MODEL_LENGTH > 0) {
            return false;
        }

        return renderInFirstPerson;
    }
}
