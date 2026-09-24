package com.projectredserverguifix.mixin;

import mrtjp.projectred.core.BundledSignalsLib;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Backports the ProjectRed 4.16 Transmission API fix so external bundled
 * integrations query the canonical bundled-input implementation.
 *
 * This target lives in the optional ProjectRed Transmission module. The mixin
 * plugin enables it only when both Transmission and CC:Tweaked are installed.
 */
@Pseudo
@Mixin(targets = "mrtjp.projectred.transmission.TransmissionAPI", remap = false)
public abstract class TransmissionAPIMixin {

    @Inject(
            method = "getBundledInput",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void projectRedFixes$getBundledInput(
            World world,
            BlockPos pos,
            Direction facing,
            CallbackInfoReturnable<byte[]> cir
    ) {
        cir.setReturnValue(BundledSignalsLib.getBundledInput(world, pos, facing));
    }
}
