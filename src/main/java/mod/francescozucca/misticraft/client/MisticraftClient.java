package mod.francescozucca.misticraft.client;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.client.renderer.MortarBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class MisticraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(Misticraft.MORTAR_BET, MortarBlockEntityRenderer::new);
    }
}
