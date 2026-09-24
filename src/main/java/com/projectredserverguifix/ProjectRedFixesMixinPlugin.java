package com.projectredserverguifix;

import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * Applies optional compatibility mixins only when the mods they are meant to
 * integrate with are actually present.
 */
public final class ProjectRedFixesMixinPlugin implements IMixinConfigPlugin {

    private static final String MIXIN_PACKAGE = "com.projectredserverguifix.mixin.";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals(MIXIN_PACKAGE + "BundledGatePartMixin")
                || mixinClassName.equals(MIXIN_PACKAGE + "BundledCablePartMixin")
                || mixinClassName.equals(MIXIN_PACKAGE + "TransmissionAPIMixin")) {
            return isModLoaded("computercraft") && isModLoaded("projectred-transmission");
        }

        return true;
    }

    private static boolean isModLoaded(String modId) {
        LoadingModList loadingModList = FMLLoader.getLoadingModList();
        return loadingModList != null && loadingModList.getModFileById(modId) != null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
