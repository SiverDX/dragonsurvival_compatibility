package de.cadentem.dragonsurvival_compatibility.compat.bettercombat;

import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class AnimationUtils {
    public static Player CURRENT_PLAYER;

    public static boolean isAttacking(@Nullable final Player player) {
        return player instanceof AttackAnimationAccess access && access.dragonsurvival_compatibility$hasActiveAnimation();
    }
}
