package de.cadentem.dragonsurvival_compatibility.mixin.apotheosis.affix;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "shadows.apotheosis.adventure.affix.effect.OmneticAffix$OmneticData", remap = false)
public interface OmneticDataAccessor {
    @Accessor("items")
    ItemStack[] dragonsurvival_compatibility$getItems();
}
