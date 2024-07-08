package de.cadentem.dragonsurvival_compatibility.compat.bettercombat;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;

import javax.annotation.Nullable;
import java.util.HashMap;

public class AnimationUtils {
    public static Player CURRENT_PLAYER;

    private static final HashMap<Integer, Integer> HIDE_MODEL = new HashMap<>();

    public static boolean shouldHideModel(@Nullable final Player player) {
        if (player == null) {
            return false;
        }

        return HIDE_MODEL.getOrDefault(player.getId(), 0) > 0;
    }

    public static void decrement(@Nullable final Player player) {
        if (player == null) {
            return;
        }

        HIDE_MODEL.computeIfPresent(player.getId(), (key, value) -> {
            if (value > 0) {
                return value - 1;
            }

            return value;
        });
    }

    public static void start(@Nullable final Player player, int amount) {
        if (player == null) {
            return;
        }

        HIDE_MODEL.put(player.getId(), amount);
    }

    public static void removeEntry(final EntityLeaveLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            HIDE_MODEL.remove(event.getEntity().getId());
        }
    }
}
