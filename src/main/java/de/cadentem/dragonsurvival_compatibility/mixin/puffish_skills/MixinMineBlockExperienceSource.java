package de.cadentem.dragonsurvival_compatibility.mixin.puffish_skills;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ClawToolHandler;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.util.ToolUtils;
import de.cadentem.dragonsurvival_compatibility.config.ServerConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.puffish.skillsmod.experience.builtin.MineBlockExperienceSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MineBlockExperienceSource.class)
public abstract class MixinMineBlockExperienceSource {
    /** Enable experience gain for dragon harvest tools */
    @ModifyVariable(method = "getValue", at = @At(value = "HEAD"), argsOnly = true, remap = false)
    public ItemStack dragonsurvival_compatibility$handleDragonHarvestTool(final ItemStack tool, /* Method parameters: */ final ServerPlayer player, final BlockState blockState) {
        if (ServerConfig.PUFFISH_SKILLS.get()) {
            if (ToolUtils.shouldUseDragonTools(tool)) {
                DragonStateHandler handler = DragonUtils.getHandler(player);

                if (!handler.isDragon() || handler.switchedTool) {
                    return tool;
                }

                ItemStack dragonHarvestTool = ClawToolHandler.getDragonHarvestTool(player, blockState);

                if (dragonHarvestTool == tool) {
                    return DragonUtils.getHandler(player).getFakeTool(blockState);
                } else {
                    return dragonHarvestTool;
                }
            }
        }

        return tool;
    }
}
