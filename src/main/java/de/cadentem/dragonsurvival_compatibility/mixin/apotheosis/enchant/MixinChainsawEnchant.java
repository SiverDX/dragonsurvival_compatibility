package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.enchant;

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
import shadows.apotheosis.ench.enchantments.masterwork.ChainsawEnchant;

@Mixin(value = ChainsawEnchant.class, remap = false)
public abstract class MixinChainsawEnchant {
    @Unique private Player chainsaw_player;
    @Unique private BlockState chainsaw_blockState;

    @Inject(method = "chainsaw", at = @At("HEAD"))
    public void chainsaw_storeData(final BlockEvent.BreakEvent event, final CallbackInfo callback) {
        chainsaw_player = event.getPlayer();
        chainsaw_blockState = event.getState();
    }

    @ModifyVariable(method = "chainsaw", at = @At("STORE"), ordinal = 0)
    public ItemStack chainsaw_provideDragonTool(final ItemStack itemStack) {
        return Utils.getDragonHarvestTool(itemStack, chainsaw_player, chainsaw_blockState);
    }
}
