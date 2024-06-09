package de.cadentem.dragonsurvival_compatibility.mixin;

import de.cadentem.dragonsurvival_compatibility.DragonSurvivalCompatibility;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ApplyMixinPlugin implements IMixinConfigPlugin {
    private final static String PREFIX = ApplyMixinPlugin.class.getPackageName() + ".";

    @Override
    public void onLoad(final String mixinPackage) { /* Nothing to do */ }

    @Override
    public String getRefMapperConfig() { /* Nothing to do */ return null; }

    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        String modid = mixinClassName.replace(PREFIX, "");
        modid = modid.replace("client.", "");
        String[] elements = modid.split("\\.");

        if (elements.length == 2) {
            boolean shouldApply = LoadingModList.get().getModFileById(elements[0]) != null;

            if (!shouldApply) {
                DragonSurvivalCompatibility.LOG.debug("Skipping mixin [{}] due to relevant mod not being present", mixinClassName);
            }

            return shouldApply;
        }

        return true;
    }

    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) { /* Nothing to do */ }

    @Override
    public List<String> getMixins() { /* Nothing to do */ return null; }

    @Override
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) { /* Nothing to do */ }

    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) { /* Nothing to do */ }
}
