package de.cadentem.dragonsurvival_compatibility.mixin.jade;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import de.cadentem.dragonsurvival_compatibility.Utils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.common.TierSortingRegistry;
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
    public void getAccesor(final BlockAccessor accessor, final IPluginConfig config, final IElementHelper helper, final CallbackInfoReturnable<List<IElement>> callback) {
        this.dragonsurvival_compatibility$accessor = accessor;
    }

    /** @reason Give Jade the relevant dragon claw harvest tool or a fake tool based on the dragon harvest level */
    @ModifyVariable(method = "getText", at = @At(value = "STORE"), name = "held", remap = false)
    public ItemStack change(final ItemStack itemStack) {
        if (ClientConfig.ENABLE_JADE.get()) {
            if (Utils.shouldUseDragonHarvestTools(itemStack)) {
                DragonStateHandler handler = DragonUtils.getHandler(dragonsurvival_compatibility$accessor.getPlayer());

                if (!handler.isDragon()) {
                    return itemStack;
                }

                Tier tier = handler.getDragonHarvestTier(dragonsurvival_compatibility$accessor.getBlockState());
                ItemStack clawStack = ClawToolHandler.getDragonHarvestTool(dragonsurvival_compatibility$accessor.getPlayer(), dragonsurvival_compatibility$accessor.getBlockState());

                // Main hand is not a tool or its tier is lower than the base harvest level of the dragon
                if (!(clawStack.getItem() instanceof TieredItem tieredItem) || TierSortingRegistry.getTiersLowerThan(tier).contains(tieredItem.getTier())) {
                    return handler.getFakeTool(dragonsurvival_compatibility$accessor.getBlockState());
                }

                return clawStack;
            }
        }

        return itemStack;
    }
}
