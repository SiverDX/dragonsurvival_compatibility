package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.mojang.math.Vector3f;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import dev.kosmx.playerAnim.impl.animation.AnimationApplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimationApplier.class)
public abstract class AnimationApplierMixin {
    /** @reason Adjust the Better Combat attack animation to the dragon size */
    @Inject(method = "updatePart", at = @At("TAIL"), remap = false)
    public void dragonsurvival_compatibility$offsetAttackAnimation(final String partName, final ModelPart part, final CallbackInfo callback) {
        if (ClientConfig.BETTERCOMBAT.get()) {
            Minecraft instance = Minecraft.getInstance();

            if (partName.equals("rightArm") || partName.equals("leftArm")) {
                if (instance.options.getCameraType().isFirstPerson() && instance.screen == null) {
                    DragonStateHandler handler = DragonUtils.getHandler(instance.player);

                    if (handler.isDragon()) {
                        double size = handler.getSize();
                        float yOffset = (float) (20F - size + (size * 0.4));

                        // Negative `y` value => animation is higher
                        part.offsetPos(new Vector3f(0, yOffset, 0));
                    }
                }
            }
        }
    }
}
