package de.cadentem.dragonsurvival_compatibility.mixin.bettercombat;

import de.cadentem.dragonsurvival_compatibility.compat.bettercombat.AttackAnimationAccess;
import net.bettercombat.client.animation.AttackAnimationSubStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.common.util.Lazy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.reflect.Field;

@Mixin(value = AbstractClientPlayer.class, priority = 1500)
public abstract class AbstractClientPlayerMixin implements AttackAnimationAccess {
    @Unique // Accessors etc. cannot find this field in < 1.21.1, '@Dynamic' does not fix the issue
    private final Lazy<AttackAnimationSubStack> dragonsurvival_compatibility$attackAnimation = Lazy.of(() -> {
        try {
            //noinspection JavaReflectionMemberAccess -> Added through 'net.bettercombat.mixin.client.AbstractClientPlayerEntityMixin'
            Field attackAnimation = AbstractClientPlayer.class.getDeclaredField("attackAnimation");
            return (AttackAnimationSubStack) attackAnimation.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    });

    @Override
    public boolean dragonsurvival_compatibility$hasActiveAnimation() {
        AttackAnimationSubStack stack = dragonsurvival_compatibility$attackAnimation.get();

        if (stack == null) {
            return false;
        }

        return stack.base.isActive();
    }
}
