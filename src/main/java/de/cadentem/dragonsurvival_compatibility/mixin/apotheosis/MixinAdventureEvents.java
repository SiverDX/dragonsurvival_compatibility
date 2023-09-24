package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis;

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
import shadows.apotheosis.adventure.AdventureEvents;

@Mixin(value = AdventureEvents.class, remap = false)
public class MixinAdventureEvents {
    @Unique
    private Player blockBreak_player;
    @Unique private BlockState blockBreak_blockState;

    @Inject(method = "blockBreak", at = @At("HEAD"))
    public void blockBreak_storeData(final BlockEvent.BreakEvent event, final CallbackInfo callback) {
        blockBreak_player = event.getPlayer();
        blockBreak_blockState = event.getState();
    }

    @ModifyVariable(method = "blockBreak", at = @At("STORE"), ordinal = 0)
    public ItemStack harvest_provideDragonTool(final ItemStack itemStack) {
        return Utils.getDragonHarvestTool(itemStack, blockBreak_player, blockBreak_blockState);
    }
}
