package mod.francescozucca.misticraft.client;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.client.renderer.MortarBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.PlayerScreenHandler;

@Environment(EnvType.CLIENT)
public class MisticraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Misticraft.MORTAR, RenderLayer.getCutout());
        BlockEntityRendererRegistry.register(Misticraft.MORTAR_BET, MortarBlockEntityRenderer::new);

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((spriteAtlasTexture, registry) -> {
            registry.register(Misticraft.id("rose_incense"));
        });

        ParticleFactoryRegistry.getInstance().register(Misticraft.ROSE_INCENSE_PT, FlameParticle.Factory::new);
    }
}
