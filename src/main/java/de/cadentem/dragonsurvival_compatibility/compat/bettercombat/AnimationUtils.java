package de.cadentem.dragonsurvival_compatibility.compat.bettercombat;

import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class AnimationUtils {
    public static Player CURRENT_PLAYER;
    
    public enum Type {
        BETTERCOMBAT, IRON_SPELLBOOKS;
    }

    public static boolean isAttacking(@Nullable final Player player) {
        if (player == null) {
            return false;
        }

        if (isAttacking(player, Type.BETTERCOMBAT)) {
            return true;
        }

        return isAttacking(player, Type.IRON_SPELLBOOKS);
    }

    public static boolean isAttacking(@Nullable final Player player, final Type type) {
        if (player == null) {
            return false;
        }

        return switch (type) {
            case BETTERCOMBAT:
                yield ClientConfig.BETTERCOMBAT.get() && player instanceof AttackAnimationAccess access && access.dragonsurvival_compatibility$hasActiveAnimation();
            case IRON_SPELLBOOKS:
                KeyframeAnimationPlayer animation = ClientMagicData.castingAnimationPlayerLookup.get(player.getUUID());
                yield ClientConfig.IRONS_SPELLBOOKS.get() && animation != null && animation.isActive();
        };
    }
}
