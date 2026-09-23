package com.projectredserverguifix.mixin;

import com.projectredserverguifix.ProjectRedGuiPacketBridge;
import mrtjp.projectred.integration.part.GatePart;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "mrtjp.projectred.integration.part.ComplexGatePart$Counter", remap = false)
public abstract class CounterMixin {

    @Redirect(
            method = "gateLogicActivate",
            at = @At(
                    value = "INVOKE",
                    target = "Lmrtjp/projectred/integration/gui/screen/CounterScreen;open(Lnet/minecraft/entity/player/PlayerEntity;Lmrtjp/projectred/integration/part/GatePart;)V",
                    remap = false
            ),
            remap = false
    )
    private void projectRedServerGuiFix$openCounterGui(PlayerEntity player, GatePart part) {
        ProjectRedGuiPacketBridge.openCounterGui(player, part);
    }
}
