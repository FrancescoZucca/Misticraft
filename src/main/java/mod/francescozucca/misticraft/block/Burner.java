package mod.francescozucca.misticraft.block;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.block.entity.BurnerBlockEntity;
import mod.francescozucca.misticraft.item.Incense;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Burner extends BlockWithEntity {

    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    public Burner(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BurnerBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient() && world.getBlockEntity(pos) instanceof BurnerBlockEntity be){
            ItemStack stack = player.getStackInHand(hand);
            if(be.getStack(0).isEmpty()) {
                if (stack.getItem() instanceof Incense) {
                    be.setStack(0, stack);
                    player.setStackInHand(hand, ItemStack.EMPTY);
                    return ActionResult.SUCCESS;
                }
                return ActionResult.PASS;
            }else{
                player.getInventory().offerOrDrop(be.getStack(0));
                be.setStack(0, ItemStack.EMPTY);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(1d/16d, 0d, 1d/16d, 15d/16d, 12d/16d, 15d/16d);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Misticraft.BURNER_BET, BurnerBlockEntity::tick);
    }
}
