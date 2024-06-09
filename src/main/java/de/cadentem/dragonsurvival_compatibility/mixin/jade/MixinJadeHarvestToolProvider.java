package de.cadentem.dragonsurvival_compatibility.mixin.jade;

import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.jade.addon.harvest.HarvestToolProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import java.util.List;

@Mixin(HarvestToolProvider.class)
public abstract class MixinJadeHarvestToolProvider {
    @Unique private BlockAccessor dragonsurvival_compatibility$accessor;

    /** @reason Get {@link net.minecraft.world.entity.player.Player} and {@link net.minecraft.world.level.block.state.BlockState} */
    @Inject(method = "getText", at = @At("HEAD"), remap = false)
    public void dragonsurvival_compatibility$getAccesor(final BlockAccessor accessor, final IPluginConfig config, final IElementHelper helper, final CallbackInfoReturnable<List<IElement>> callback) {
        this.dragonsurvival_compatibility$accessor = accessor;
    }

    /** @reason Give Jade the relevant dragon claw harvest tool or a fake tool based on the dragon harvest level */
    @ModifyVariable(method = "getText", at = @At(value = "STORE"), name = "held", remap = false)
    public ItemStack dragonsurvival_compatibility$change(final ItemStack stack) {
        if (ClientConfig.JADE.get()) {
            return Utils.getTooltipStack(stack, dragonsurvival_compatibility$accessor.getPlayer(), dragonsurvival_compatibility$accessor.getBlockState());
        }

        return stack;
    }
}
