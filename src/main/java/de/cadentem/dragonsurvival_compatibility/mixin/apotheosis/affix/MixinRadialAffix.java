package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix;

import de.cadentem.dragonsurvival_compatibility.utils.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadows.apotheosis.adventure.affix.effect.RadialAffix;

@Mixin(value = RadialAffix.class, remap = false)
public class MixinRadialAffix {
    @Unique private Player onBreak_player;
    @Unique private BlockState onBreak_blockState;

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void onBreak_storeData(final BlockEvent.BreakEvent event, final CallbackInfo callback) {
        onBreak_player = event.getPlayer();
        onBreak_blockState = event.getState();
    }

    @ModifyVariable(method = "onBreak", at = @At("STORE"), ordinal = 0)
    public ItemStack harvest_provideDragonTool(final ItemStack itemStack) {
        return Utils.getDragonHarvestTool(itemStack, onBreak_player, onBreak_blockState);
    }
}
