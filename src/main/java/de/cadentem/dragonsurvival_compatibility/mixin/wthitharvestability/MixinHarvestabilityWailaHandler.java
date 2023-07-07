package de.cadentem.dragonsurvival_compatibility.mixin.wthitharvestability;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.wthitharvestability.WailaHandler;

import java.util.List;

@Mixin(WailaHandler.class)
public class MixinHarvestabilityWailaHandler {
    private Player player;
    private BlockState blockState;

    /** @reason Get {@link Player} and {@link BlockState} */
    @Inject(method = "getHarvestability", at = @At("HEAD"), remap = false)
    public void getPlayer(final List<Component> stringList, final Player player, final BlockState blockState, final BlockPos pos, final mcp.mobius.waila.api.IPluginConfig config, boolean minimalLayout, CallbackInfo callback) {
        this.player = player;
        this.blockState = blockState;
    }

    /** @reason Give WTHIT the relevant dragon claw harvest tool or a fake tool based on the dragon harvest level */
    @ModifyVariable(method = "getHarvestability", at = @At(value = "STORE"), name = "heldStack", remap = false)
    public ItemStack change(final ItemStack itemStack) {
        if (ClientConfig.ENABLE_WTHITHARVESTABILITY.get()) {
            DragonStateHandler handler = DragonUtils.getHandler(player);

            if (handler.isDragon()) {
                Tier tier = handler.getDragonHarvestTier(blockState);
                // TODO :: Cache this?
                ItemStack clawStack = ClawToolHandler.getDragonHarvestTool(player);

                // Main hand is not a tool or its tier is lower than the base harvest level of the dragon
                if (!(clawStack.getItem() instanceof TieredItem tieredItem) || TierSortingRegistry.getTiersLowerThan(tier).contains(tieredItem.getTier())) {
                    return handler.getFakeTool(blockState);
                }

                return clawStack;
            }
        }

        return itemStack;
    }
}
