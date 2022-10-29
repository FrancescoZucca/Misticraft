package mod.francescozucca.misticraft.client;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.client.renderer.MortarBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class MisticraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Misticraft.MORTAR, RenderLayer.getCutout());
        BlockEntityRendererRegistry.register(Misticraft.MORTAR_BET, MortarBlockEntityRenderer::new);
    }
}
