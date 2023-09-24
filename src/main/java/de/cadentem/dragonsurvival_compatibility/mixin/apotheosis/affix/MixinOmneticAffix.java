package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix;

import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.adventure.affix.effect.OmneticAffix;

@Mixin(value = OmneticAffix.class, remap = false)
public abstract class MixinOmneticAffix {
    @Unique private Player harvest_player;
    @Unique private BlockState harvest_blockState;

    @Inject(method = "harvest", at = @At("HEAD"))
    public void harvest_storeData(final PlayerEvent.HarvestCheck event, final CallbackInfo callback) {
        harvest_player = event.getEntity();
        harvest_blockState = event.getTargetBlock();
    }

    @ModifyVariable(method = "harvest", at = @At("STORE"), ordinal = 0)
    public ItemStack harvest_provideDragonTool(final ItemStack itemStack) {
        return Utils.getDragonHarvestTool(itemStack, harvest_player, harvest_blockState);
    }

    @Unique private Player speed_player;
    @Unique private BlockState speed_blockState;

    @Inject(method = "speed", at = @At("HEAD"))
    public void speed_storeData(final PlayerEvent.BreakSpeed event, final CallbackInfo callback) {
        speed_player = event.getEntity();
        speed_blockState = event.getState();
    }

    @ModifyVariable(method = "speed", at = @At("STORE"), ordinal = 0)
    public ItemStack speed_provideDragonTool(final ItemStack itemStack) {
        return Utils.getDragonHarvestTool(itemStack, speed_player, speed_blockState);
    }

    @Unique private static Player getBaseSpeed_player;
    @Unique private static BlockState getBaseSpeed_blockState;

    @Inject(method = "getBaseSpeed", at = @At("HEAD"))
    private static void getBaseSpeed_storeData(final Player player, final ItemStack itemStack, final BlockState blockState, final BlockPos blockPosition, final CallbackInfoReturnable<Float> callback) {
        getBaseSpeed_player = player;
        getBaseSpeed_blockState = blockState;
    }

    @ModifyVariable(method = "getBaseSpeed", at = @At("STORE"), ordinal = 1)
    private static ItemStack getBaseSpeed_provideDragonTool(final ItemStack itemStack) {
        return Utils.getDragonHarvestTool(itemStack, getBaseSpeed_player, getBaseSpeed_blockState);
    }
}
