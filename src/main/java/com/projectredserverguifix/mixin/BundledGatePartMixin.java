package com.projectredserverguifix.mixin;

import codechicken.lib.vec.Rotation;
import mrtjp.projectred.core.BundledSignalsLib;
import mrtjp.projectred.core.FaceLookup;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Backports the bundled-interaction side calculation fixed upstream for
 * ProjectRed 4.16. This mixin is enabled only when CC:Tweaked and ProjectRed
 * Transmission are both present.
 */
@Mixin(targets = "mrtjp.projectred.integration.part.BundledGatePart", remap = false)
public abstract class BundledGatePartMixin {

    @Redirect(
            method = "resolveArray",
            at = @At(
                    value = "INVOKE",
                    target = "Lmrtjp/projectred/core/BundledSignalsLib;getBundledSignalViaInteraction(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;)[B",
                    remap = false
            ),
            remap = false
    )
    private byte[] projectRedFixes$correctBundledInteractionSide(
            World world,
            BlockPos pos,
            Direction ignoredSide,
            FaceLookup lookup
    ) {
        Direction correctedSide = Direction.values()[Rotation.rotateSide(lookup.otherSide, lookup.otherRotation)];
        return BundledSignalsLib.getBundledSignalViaInteraction(world, pos, correctedSide);
    }
}
