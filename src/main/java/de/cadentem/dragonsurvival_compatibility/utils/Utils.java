package de.cadentem.dragonsurvival_compatibility.utils;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {
    public static ItemStack getDragonHarvestTool(final ItemStack original, final Player player, final BlockState blockState) {
        return getDragonHarvestTool(original, player, blockState, DragonUtils.getHandler(player));
    }

    public static ItemStack getDragonHarvestTool(final ItemStack original, final Player player, final BlockState blockState, final DragonStateHandler handler) {
        if (player != null && blockState != null) {
            if (handler.isDragon()) {
                SimpleContainer clawsInventory = handler.getClawToolData().getClawsInventory();

                if (!ToolUtils.shouldUseDragonTools(original)) {
                    return original;
                }

                if (blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) && clawsInventory.getItem(1) != ItemStack.EMPTY) {
                    return clawsInventory.getItem(1);
                } else if (blockState.is(BlockTags.MINEABLE_WITH_AXE) && clawsInventory.getItem(2) != ItemStack.EMPTY) {
                    return clawsInventory.getItem(2);
                } else if (blockState.is(BlockTags.MINEABLE_WITH_SHOVEL) && clawsInventory.getItem(3) != ItemStack.EMPTY) {
                    return clawsInventory.getItem(3);
                }

                return ClawToolHandler.getDragonHarvestTool(player, blockState);
            }
        }

        return original;
    }

    public static ItemStack getDragonWeapon(final ItemStack original, final Player player) {
        ItemStack result = original;

        if (DragonUtils.isDragon(player)) {
            ItemStack dragonWeapon = ClawToolHandler.getDragonSword(player);

            if (dragonWeapon != ItemStack.EMPTY) {
                result = dragonWeapon;
            }
        }

        return result;
    }
}
