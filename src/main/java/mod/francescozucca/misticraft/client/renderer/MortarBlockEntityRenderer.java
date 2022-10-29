package mod.francescozucca.misticraft.client.renderer;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.blocks.Mortar;
import mod.francescozucca.misticraft.blocks.entity.MortarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class MortarBlockEntityRenderer implements BlockEntityRenderer<MortarBlockEntity> {

    public MortarBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){}

    @Override
    public void render(MortarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(!(entity.getWorld().getBlockEntity(entity.getPos()) instanceof MortarBlockEntity) || entity.getWorld().getBlockState(entity.getPos()).getBlock() != Misticraft.MORTAR) return;

        ItemStack stack = entity.getStack(0);

        boolean empty = entity.getWorld().getBlockState(entity.getPos()).get(Mortar.EMPTY);

        if(empty){
            stack = ItemStack.EMPTY;
        }

        matrices.push();
        matrices.translate(0.5, 0.85, 0.5);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 4f));

        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
        if(!stack.isEmpty())
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

        matrices.pop();
    }
}
