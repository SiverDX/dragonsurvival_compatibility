package de.cadentem.dragonsurvival_compatibility.mixin.wthitharvestability;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import de.cadentem.dragonsurvival_compatibility.config.ClientConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.wthitharvestability.WailaHandler;

import java.util.List;

@Mixin(WailaHandler.class)
public abstract class MixinHarvestabilityWailaHandler {
    @Unique private Player dragonsurvival_compatibility$player;
    @Unique private BlockState dragonsurvival_compatibility$blockState;

    /** @reason Get {@link Player} and {@link BlockState} */
    @Inject(method = "getHarvestability", at = @At("HEAD"), remap = false)
    public void dragonsurvival_compatibility$getPlayer(final List<Component> stringList, final Player player, final BlockState blockState, final BlockPos pos, final mcp.mobius.waila.api.IPluginConfig config, boolean minimalLayout, CallbackInfo callback) {
        this.dragonsurvival_compatibility$player = player;
        this.dragonsurvival_compatibility$blockState = blockState;
    }

    /** @reason Give WTHIT the relevant dragon claw harvest tool or a fake tool based on the dragon harvest level */
    @ModifyVariable(method = "getHarvestability", at = @At(value = "STORE"), name = "heldStack", remap = false)
    public ItemStack dragonsurvival_compatibility$change(final ItemStack itemStack) {
        if (ClientConfig.WTHITHARVESTABILITY.get()) {
            if (ToolUtils.shouldUseDragonTools(itemStack)) {
                DragonStateHandler handler = DragonUtils.getHandler(dragonsurvival_compatibility$player);

                if (!handler.isDragon() || handler.switchedTool) {
                    return itemStack;
                }

                Tier tier = handler.getDragonHarvestTier(dragonsurvival_compatibility$blockState);
                ItemStack clawStack = ClawToolHandler.getDragonHarvestTool(dragonsurvival_compatibility$player, dragonsurvival_compatibility$blockState);

                // Main hand is not a tool or its tier is lower than the base harvest level of the dragon
                if (!(clawStack.getItem() instanceof TieredItem tieredItem) || TierSortingRegistry.getTiersLowerThan(tier).contains(tieredItem.getTier())) {
                    return handler.getFakeTool(dragonsurvival_compatibility$blockState);
                }

                return clawStack;
            }
        }

        return itemStack;
    }
}
