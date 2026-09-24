package com.projectredserverguifix.mixin;

import mrtjp.projectred.core.BundledSignalsLib;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Backports the Bus Converter bit-scan fixes from newer ProjectRed versions.
 *
 * ProjectRed 4.15.0 uses signed right shifts in mostSignificantBit(), which can
 * loop forever after a 16-bit bundled mask is sign-extended. Masking to 16 bits
 * and using unsigned shifts preserves the intended 0-15 bundled-channel index.
 */
@Mixin(value = BundledSignalsLib.class, remap = false)
public abstract class BundledSignalsLibMixin {

    @Inject(
            method = "mostSignificantBit",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void projectRedFixes$mostSignificantBit(int mask, CallbackInfoReturnable<Integer> cir) {
        int idx = 0;
        int remaining = (mask & 0xFFFF) >>> 1;

        while (remaining != 0) {
            remaining >>>= 1;
            idx++;
        }

        cir.setReturnValue(idx);
    }
}
