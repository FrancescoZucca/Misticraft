package mod.francescozucca.misticraft.blocks;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.blocks.entity.MortarBlockEntity;
import mod.francescozucca.misticraft.recipes.MortarRecipe;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickScheduler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Mortar extends BlockWithEntity {

    public static final BooleanProperty EMPTY = BooleanProperty.of("empty");
    public static final int COOLDOWN_TIME = 10;
    public Mortar(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(EMPTY, true));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof MortarBlockEntity mbe && !world.isClient()) {

            BlockState curState = world.getBlockState(pos);

            if(mbe.cooldown == 0 && player.getStackInHand(hand).getItem() == Misticraft.PESTLE) {

                Optional<MortarRecipe> match = world.getRecipeManager().getFirstMatch(MortarRecipe.Type.INSTANCE, mbe, world);
                if(match.isPresent()) {
                    world.playSound(player, pos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1, 1);
                    mbe.cooldown = COOLDOWN_TIME;
                    player.getInventory().offerOrDrop(match.get().getOutput().copy());
                    mbe.getStack(0).decrement(1);
                    world.updateListeners(pos, curState, world.getBlockState(pos), Block.NOTIFY_LISTENERS);
                    mbe.markDirty();
                }
            }else if(player.getStackInHand(hand).getItem() != Misticraft.PESTLE){
                if(mbe.getStack(0).isEmpty()){
                    ItemStack is = player.getStackInHand(hand);
                    mbe.setStack(0, is);
                    player.setStackInHand(hand, ItemStack.EMPTY);
                }else{
                    player.getInventory().offerOrDrop(mbe.getStack(0).copy());
                    mbe.setStack(0, ItemStack.EMPTY);
                }
                world.updateListeners(pos, curState, world.getBlockState(pos), Block.NOTIFY_LISTENERS);
                mbe.markDirty();
            }

            if(state.get(EMPTY) ^ mbe.isEmpty()){
                world.setBlockState(pos, state.with(EMPTY, mbe.isEmpty()));
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MortarBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(.125f, 0f, .125f, 0.875f, 0.75f, 0.875f);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Misticraft.MORTAR_BET, MortarBlockEntity::tick);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(EMPTY);
    }
}
