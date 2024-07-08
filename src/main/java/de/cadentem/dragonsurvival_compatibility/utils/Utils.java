package de.cadentem.dragonsurvival_compatibility.utils;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.subcapabilities.ClawInventory;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;

public class Utils {
    public static ItemStack getDragonHarvestTool(final ItemStack original, final Player player, final BlockState blockState) {
        return getDragonHarvestTool(original, player, blockState, DragonUtils.getHandler(player));
    }

    public static ItemStack getDragonHarvestTool(final ItemStack original, final Player player, final BlockState blockState, final DragonStateHandler handler) {
        if (player != null && blockState != null) {
            if (handler.isDragon()) {

                if (handler.switchedTool) {
                    return original;
                }

                SimpleContainer clawsInventory = handler.getClawToolData().getClawsInventory();

                if (!ToolUtils.shouldUseDragonTools(original)) {
                    return original;
                }

                ItemStack pickaxe = clawsInventory.getItem(ClawInventory.Slot.PICKAXE.ordinal());
                ItemStack axe = clawsInventory.getItem(ClawInventory.Slot.AXE.ordinal());
                ItemStack shovel = clawsInventory.getItem(ClawInventory.Slot.SHOVEL.ordinal());

                if (!pickaxe.isEmpty() && blockState.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
                    return pickaxe;
                } else if (!axe.isEmpty() && blockState.is(BlockTags.MINEABLE_WITH_AXE)) {
                    return axe;
                } else if (!shovel.isEmpty() && blockState.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
                    return shovel;
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

    public static float getHarvestSpeed(final ItemStack itemStack, final BlockState blockState) {
        return itemStack.getItem() instanceof DiggerItem diggerItem ? diggerItem.getDestroySpeed(itemStack, blockState) : itemStack.getDestroySpeed(blockState);
    }

    public static ItemStack getTooltipStack(final ItemStack tool, final Player player, final BlockState state) {
        if (state == null) {
            return tool;
        }

        if (ToolUtils.shouldUseDragonTools(tool)) {
            DragonStateHandler handler = DragonUtils.getHandler(player);

            if (!handler.isDragon() || handler.switchedTool) {
                return tool;
            }

            Tier tier = handler.getDragonHarvestTier(state);
            ItemStack clawTool = ClawToolHandler.getDragonHarvestTool(player, state);

            // Main hand is not a tool or its tier is lower than the base harvest level of the dragon
            if (!(clawTool.getItem() instanceof TieredItem tieredItem) || TierSortingRegistry.getTiersLowerThan(tier).contains(tieredItem.getTier())) {
                return handler.getFakeTool(state);
            }

            return clawTool;
        }

        return tool;
    }
}
