package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import de.cadentem.dragonsurvival_compatibility.compat.bettercombat.AnimationUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Avoid rendering the floating weapon */
@Mixin(value = LevelRenderer.class, priority = 1500)
public abstract class LevelRendererMixin {
    @TargetHandler(mixin = "by.dragonsurvivalteam.dragonsurvival.mixins.MixinWorldRenderer", name = "render")
    @ModifyExpressionValue(method = "@MixinSquared:Handler", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, ordinal = 0))
    private Boolean dragonsurvival_compatibility$hideBodyPart(final Boolean renderInFirstPerson, @Local(argsOnly = true) final Camera camera) {
        if (ClientConfig.BETTERCOMBAT.get() && Minecraft.getInstance().options.getCameraType().isFirstPerson() && camera.getEntity() instanceof Player player && AnimationUtils.shouldHideModel(player)) {
            return false;
        }

        return renderInFirstPerson;
    }
}
