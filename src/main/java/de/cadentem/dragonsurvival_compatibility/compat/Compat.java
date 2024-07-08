package de.cadentem.dragonsurvival_compatibility.compat;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;

import java.util.HashMap;
import java.util.Map;

public class Compat {
    private static final Map<Mod, Boolean> MODS = new HashMap<>();

    public static boolean isModLoaded(final Mod mod) {
        return MODS.computeIfAbsent(mod, key -> {
            ModList modList = ModList.get();

            if (modList != null) {
                return modList.isLoaded(key.modId);
            }

            return LoadingModList.get().getModFileById(key.modId) != null;
        });
    }

    public enum Mod {
        BETTERCOMBAT("bettercombat"),
        UPGRADED_NETHERITE("upgradednetherite"),
        COLD_SWEAT("cold_sweat");

        public final String modId;

        Mod(final String modId) {
            this.modId = modId;
        }
    }
}
