package de.cadentem.dragonsurvival_compatibility.mixin.attributeslib;

import by.dragonsurvivalteam.dragonsurvival.data.DataDamageTypeTagsProvider;
import by.dragonsurvivalteam.dragonsurvival.registry.DSDamageTypes;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import dev.shadowsoffire.attributeslib.impl.AttributeEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Dragon breath shouldn't constantly trigger the melee damage attacks */
@Debug(export = true)
@Mixin(value = AttributeEvents.class, remap = false)
public abstract class AttributeEventsMixin {
    @Inject(method = "meleeDamageAttributes", at = @At("HEAD"), cancellable = true)
    private void dragon_surival_compatibility$skipForBreathDamage(final LivingAttackEvent event, final CallbackInfo callback) {
        if (!ServerConfig.APOTHEOSIS.get()) {
            return;
        }

        DamageSource source = event.getSource();

        if (source.is(DataDamageTypeTagsProvider.DRAGON_BREATH) || source.is(DSDamageTypes.CAVE_DRAGON_BURN) || source.is(DSDamageTypes.FOREST_DRAGON_DRAIN)) {
            callback.cancel();
        }
    }
}
