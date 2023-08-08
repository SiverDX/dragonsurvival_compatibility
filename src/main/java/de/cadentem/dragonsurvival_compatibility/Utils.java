package de.cadentem.dragonsurvival_compatibility;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;

public class Utils {
    public static boolean shouldUseDragonHarvestTools(final ItemStack itemStack) {
        return !(itemStack.getItem() instanceof TieredItem) && !isHarvestTool(itemStack) && !isWeapon(itemStack);
    }

    public static boolean isHarvestTool(final ItemStack itemStack) {
        return isPickaxe(itemStack) || isAxe(itemStack) || isShovel(itemStack) || isHoe(itemStack) || itemStack.is(Tags.Items.SHEARS);
    }

    public static boolean isWeapon(final ItemStack itemStack) {
        return itemStack.canPerformAction(ToolActions.SWORD_DIG) || itemStack.canPerformAction(ToolActions.SWORD_SWEEP);
    }

    public static boolean isPickaxe(final ItemStack itemStack) {
        return itemStack.canPerformAction(ToolActions.PICKAXE_DIG);
    }

    public static boolean isAxe(final ItemStack itemStack) {
        return itemStack.canPerformAction(ToolActions.AXE_STRIP) || itemStack.canPerformAction(ToolActions.AXE_DIG) || itemStack.canPerformAction(ToolActions.AXE_SCRAPE);
    }

    public static boolean isShovel(final ItemStack itemStack) {
        return itemStack.canPerformAction(ToolActions.SHOVEL_FLATTEN) || itemStack.canPerformAction(ToolActions.SHOVEL_DIG);
    }

    public static boolean isHoe(final ItemStack itemStack) {
        return itemStack.canPerformAction(ToolActions.HOE_DIG) || itemStack.canPerformAction(ToolActions.HOE_TILL);
    }
}
