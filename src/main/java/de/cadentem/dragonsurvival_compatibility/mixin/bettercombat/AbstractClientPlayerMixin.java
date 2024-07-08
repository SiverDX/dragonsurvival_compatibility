package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.bawnorton.mixinsquared.TargetHandler;
import de.cadentem.dragonsurvival_compatibility.compat.bettercombat.AnimationUtils;
import net.bettercombat.logic.AnimatedHand;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractClientPlayer.class, priority = 1500, remap = false)
public abstract class AbstractClientPlayerMixin {
    @TargetHandler(mixin = "net.bettercombat.mixin.client.AbstractClientPlayerEntityMixin", name = "playAttackAnimation")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "HEAD"))
    private void dragonsurvival_compatibility$hideBodyPart(final String name, final AnimatedHand animatedHand, float length, float upswing, final CallbackInfo callback) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;

        if (DragonUtils.isDragon(player)) {
            AnimationUtils.start(player, (int) (length * upswing + length));
        }
    }
}
