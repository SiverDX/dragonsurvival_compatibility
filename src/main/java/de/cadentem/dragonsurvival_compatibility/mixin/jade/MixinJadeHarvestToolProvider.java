package de.cadentem.dragonsurvival_compatibility.mixin.jade;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
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

import java.util.List;

@Mixin(HarvestToolProvider.class)
public abstract class MixinJadeHarvestToolProvider {
    /** @reason Get {@link net.minecraft.world.entity.player.Player} and {@link net.minecraft.world.level.block.state.BlockState} */
    @Inject(method = "getText", at = @At("HEAD"), remap = false)
    public void dragonsurvival_compatibility$getAccesor(final BlockAccessor accessor, final IPluginConfig config, final CallbackInfoReturnable<List<IElement>> callback, @Share("blockAccessor") final LocalRef<BlockAccessor> blockAccessorStorage) {
        blockAccessorStorage.set(accessor);
    }

    /** @reason Give Jade the relevant dragon claw harvest tool or a fake tool based on the dragon harvest level */
    @ModifyVariable(method = "getText", at = @At(value = "STORE"), name = "held", remap = false)
    public ItemStack dragonsurvival_compatibility$change(final ItemStack itemStack, @Share("blockAccessor") final LocalRef<BlockAccessor> blockAccessorStorage) {
        if (ClientConfig.JADE.get()) {
            BlockAccessor blockAccessor = blockAccessorStorage.get();

            if (blockAccessor == null) {
                return itemStack;
            }

            if (ToolUtils.shouldUseDragonTools(itemStack)) {
                DragonStateHandler handler = DragonUtils.getHandler(blockAccessor.getPlayer());

                if (!handler.isDragon() || handler.switchedTool) {
                    return itemStack;
                }

                Tier tier = handler.getDragonHarvestTier(blockAccessor.getBlockState());
                ItemStack clawStack = ClawToolHandler.getDragonHarvestTool(blockAccessor.getPlayer(), blockAccessor.getBlockState());

                // Main hand is not a tool or its tier is lower than the base harvest level of the dragon
                if (!(clawStack.getItem() instanceof TieredItem tieredItem) || TierSortingRegistry.getTiersLowerThan(tier).contains(tieredItem.getTier())) {
                    return handler.getFakeTool(blockAccessor.getBlockState());
                }

                return clawStack;
            }
        }

        return itemStack;
    }
}
