package com.projectredserverguifix;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(ProjectRedServerGuiFix.MOD_ID)
public final class ProjectRedServerGuiFix {

    public static final String MOD_ID = "projectred_server_gui_fix";

    public ProjectRedServerGuiFix() {
        ModLoadingContext.get().registerExtensionPoint(
                ExtensionPoint.DISPLAYTEST,
                () -> Pair.of(
                        () -> FMLNetworkConstants.IGNORESERVERONLY,
                        (remoteVersion, isNetwork) -> true
                )
        );
    }
}
