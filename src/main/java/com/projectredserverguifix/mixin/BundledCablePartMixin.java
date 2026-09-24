package com.projectredserverguifix.mixin;

import codechicken.lib.vec.Rotation;
import mrtjp.projectred.core.BundledSignalsLib;
import mrtjp.projectred.core.FaceLookup;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Corrects the bundled-interaction side used by face bundled cables.
 *
 * This target lives in the optional ProjectRed Transmission module. The mixin
 * plugin enables it only when both Transmission and CC:Tweaked are installed.
 */
@Pseudo
@Mixin(targets = "mrtjp.projectred.transmission.part.BundledCablePart", remap = false)
public abstract class BundledCablePartMixin {

    @Redirect(
            method = "resolveSignal",
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
