package de.cadentem.dragonsurvival_compatibility.mixin.wthit;

import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.plugin.harvest.provider.HarvestProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = HarvestProvider.class, remap = false)
public abstract class HarvestProviderMixin {
    @ModifyVariable(method = "appendBody", at = @At(value = "STORE"), name = "heldStack")
    private ItemStack dragonsurvival_compatibility$change(final ItemStack stack, final ITooltip tooltip, final IBlockAccessor accessor) {
        if (ClientConfig.WTHIT.get()) {
            return Utils.getTooltipStack(stack, accessor.getPlayer(), accessor.getBlockState());
        }

        return stack;
    }

    @ModifyVariable(method = "onHandleTooltip", at = @At(value = "STORE"), name = "heldStack")
    private ItemStack dragonsurvival_compatibility$change(final ItemStack stack, final ITooltip tooltip, final ICommonAccessor accessor) {
        if (ClientConfig.WTHIT.get()) {
            return Utils.getTooltipStack(stack, accessor.getPlayer(), state);
        }

        return stack;
    }

    @Shadow private BlockState state;
}
