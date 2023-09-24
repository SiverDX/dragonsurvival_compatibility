package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.network.NetworkHandler;
import by.dragonsurvivalteam.dragonsurvival.network.claw.SyncDragonClawsMenu;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.util.BlockUtil;

import java.util.UUID;

@Mixin(value = BlockUtil.class, remap = false)
public abstract class MixinBlockUtil {
    /** The chainsaw enchant task posts the PlayerDestroyItemEvent as a fake player (i.e. not a dragon) */
    @Inject(method = "breakExtraBlock", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/ForgeEventFactory;onPlayerDestroyItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)V"))
    private static void checkFakePlayer(final ServerLevel level, final BlockPos blockPosition, final ItemStack mainHand, final UUID source, final CallbackInfoReturnable<Boolean> callback) {
        Player realPlayer = level.getPlayerByUUID(source);
        DragonStateHandler handler = DragonUtils.getHandler(level.getPlayerByUUID(source));

        if (handler.isDragon()) {
            SimpleContainer clawsInventory = handler.getClawToolData().getClawsInventory();

            for(int i = 0; i < 4; ++i) {
                ItemStack dragonTool = clawsInventory.getItem(i);
                if (mainHand.getItem() == dragonTool.getItem()) {
                    clawsInventory.setItem(i, ItemStack.EMPTY);
                }
            }

            NetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> realPlayer), new SyncDragonClawsMenu(realPlayer.getId(), handler.getClawToolData().isMenuOpen(), clawsInventory));
        }
    }
}
