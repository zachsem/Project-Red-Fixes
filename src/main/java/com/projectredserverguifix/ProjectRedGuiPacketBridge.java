package com.projectredserverguifix;

import codechicken.lib.packet.PacketCustom;
import mrtjp.projectred.integration.IntegrationNetwork;
import mrtjp.projectred.integration.part.GatePart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

/**
 * Server-safe bridge for ProjectRed's existing GUI-open packets.
 *
 * ProjectRed 4.15.0 puts the packet-send helpers on TimerScreen/CounterScreen,
 * which causes dedicated servers to resolve client-only Screen classes.
 * This class sends the existing ProjectRed packets without touching any
 * client GUI class.
 */
public final class ProjectRedGuiPacketBridge {

    private ProjectRedGuiPacketBridge() {
    }

    public static void openTimerGui(PlayerEntity player, GatePart part) {
        if (!(player instanceof ServerPlayerEntity)) {
            return;
        }

        PacketCustom packet = new PacketCustom(
                IntegrationNetwork.NET_CHANNEL,
                IntegrationNetwork.OPEN_TIMER_GUI_FROM_SERVER
        );
        IntegrationNetwork.writePartIndex(packet, part);
        packet.sendToPlayer((ServerPlayerEntity) player);
    }

    public static void openCounterGui(PlayerEntity player, GatePart part) {
        if (!(player instanceof ServerPlayerEntity)) {
            return;
        }

        PacketCustom packet = new PacketCustom(
                IntegrationNetwork.NET_CHANNEL,
                IntegrationNetwork.OPEN_COUNTER_GUI_FROM_SERVER
        );
        IntegrationNetwork.writePartIndex(packet, part);
        packet.sendToPlayer((ServerPlayerEntity) player);
    }
}
