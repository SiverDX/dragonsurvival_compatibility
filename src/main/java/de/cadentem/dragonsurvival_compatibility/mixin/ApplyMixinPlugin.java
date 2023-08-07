package de.cadentem.dragonsurvival_compatibility.mixin;

import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ApplyMixinPlugin implements IMixinConfigPlugin {
    private final Set<String> bettercombat = Set.of(
            "de.cadentem.dragonsurvival_compatibility.mixin.bettercombat.MixinAnimationApplier",
            "de.cadentem.dragonsurvival_compatibility.mixin.bettercombat.MixinClientDragonRender",
            "de.cadentem.dragonsurvival_compatibility.mixin.bettercombat.MixinLevelRenderer",
            "de.cadentem.dragonsurvival_compatibility.mixin.bettercombat.MixinPlayer"
    );

    private final Set<String> coldsweat = Set.of(
            "de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat.MixinPlayerTempCap",
            "de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat.MixinTempEffectsClient",
            "de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat.MixinTempEffectsCommon",
            "de.cadentem.dragonsurvival_compatibility.mixin.cold_sweat.PreventPlayerSleep"
    );

    private final Set<String> puffish_skills = Set.of(
            "de.cadentem.dragonsurvival_compatibility.mixin.puffish_skills.MixinClawToolHandler",
            "de.cadentem.dragonsurvival_compatibility.mixin.puffish_skills.MixinMineBlockExperienceSource"
    );

    @Override
    public void onLoad(final String mixinPackage) { /* Nothing to do */ }

    @Override
    public String getRefMapperConfig() {
        /* Nothing to do */
        return null;
    }

    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        // `ModList.get()` is not available at this point in time
        if (bettercombat.contains(mixinClassName)) {
            return LoadingModList.get().getModFileById("bettercombat") != null;
        }

        if (coldsweat.contains(mixinClassName)) {
            return LoadingModList.get().getModFileById("cold_sweat") != null;
        }

        if (puffish_skills.contains(mixinClassName)) {
            return LoadingModList.get().getModFileById("puffish_skills") != null;
        }

        if (mixinClassName.equals("de.cadentem.dragonsurvival_compatibility.mixin.jade.MixinJadeHarvestToolProvider")) {
            return LoadingModList.get().getModFileById("jade") != null;
        }

        if (mixinClassName.equals("de.cadentem.dragonsurvival_compatibility.mixin.wthitharvestability.MixinHarvestabilityWailaHandler")) {
            return LoadingModList.get().getModFileById("wthitharvestability") != null;
        }

        return true;
    }

    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) { /* Nothing to do */ }

    @Override
    public List<String> getMixins() {
        /* Nothing to do */
        return null;
    }

    @Override
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) { /* Nothing to do */ }

    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) { /* Nothing to do */ }
}
