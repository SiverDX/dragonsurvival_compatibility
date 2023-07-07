package de.cadentem.dragonsurvival_compatibility.mixin.jade;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.common.TierSortingRegistry;
import org.spongepowered.asm.mixin.Mixin;
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
public class MixinJadeHarvestToolProvider {
    private BlockAccessor accessor;

    /** @reason Get {@link net.minecraft.world.entity.player.Player} and {@link net.minecraft.world.level.block.state.BlockState} */
    @Inject(method = "getText", at = @At("HEAD"), remap = false)
    public void getAccesor(final BlockAccessor accessor, final IPluginConfig config, final IElementHelper helper, final CallbackInfoReturnable<List<IElement>> callback) {
        this.accessor = accessor;
    }

    /** @reason Give Jade the relevant dragon claw harvest tool or a fake tool based on the dragon harvest level */
    @ModifyVariable(method = "getText", at = @At(value = "STORE"), name = "held", remap = false)
    public ItemStack change(final ItemStack itemStack) {
        if (ClientConfig.ENABLE_JADE.get()) {
            DragonStateHandler handler = DragonUtils.getHandler(accessor.getPlayer());

            if (handler.isDragon()) {
                Tier tier = handler.getDragonHarvestTier(accessor.getBlockState());
                // TODO :: Cache this?
                ItemStack clawStack = ClawToolHandler.getDragonHarvestTool(accessor.getPlayer());

                // Main hand is not a tool or its tier is lower than the base harvest level of the dragon
                if (!(clawStack.getItem() instanceof TieredItem tieredItem) || TierSortingRegistry.getTiersLowerThan(tier).contains(tieredItem.getTier())) {
                    return handler.getFakeTool(accessor.getBlockState());
                }

                return clawStack;
            }
        }

        return itemStack;
    }
}
