package de.cadentem.dragonsurvival_compatibility.compat.bettercombat;

import de.cadentem.dragonsurvival_compatibility.compat.Compat;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class AnimationUtils {
    public static Player CURRENT_PLAYER;
    
    public enum Type {
        BETTERCOMBAT, IRONS_SPELLBOOKS;
    }

    public static boolean isAttacking(@Nullable final Player player) {
        if (player == null) {
            return false;
        }

        if (isAttacking(player, Type.BETTERCOMBAT)) {
            return true;
        }

        return isAttacking(player, Type.IRONS_SPELLBOOKS);
    }

    public static boolean isAttacking(@Nullable final Player player, final Type type) {
        if (player == null) {
            return false;
        }

        return switch (type) {
            case BETTERCOMBAT:
                yield ClientConfig.BETTERCOMBAT.get() && player instanceof AttackAnimationAccess access && access.dragonsurvival_compatibility$hasActiveAnimation();
            case IRONS_SPELLBOOKS:
                if (!ClientConfig.IRONS_SPELLBOOKS.get() || !Compat.isModLoaded(Compat.Mod.IRONS_SPELLBOOKS)) {
                    yield false;
                }

                SyncedSpellData data = ClientMagicData.getSyncedSpellData(player);
                yield data.isCasting();
        };
    }
}
