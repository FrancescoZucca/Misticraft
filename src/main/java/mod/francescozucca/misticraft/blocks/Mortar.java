package mod.francescozucca.misticraft.blocks;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.blocks.entity.MortarBlockEntity;
import mod.francescozucca.misticraft.recipes.MortarRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickScheduler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Mortar extends BlockWithEntity {

    public static final int COOLDOWN_TIME = 10;

    public static final IntProperty COOLDOWN = IntProperty.of("cooldown", 0, COOLDOWN_TIME);
    public Mortar(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(COOLDOWN, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof MortarBlockEntity mbe) {

            BlockState curState = world.getBlockState(pos);

            if(player.getStackInHand(hand).getItem() == Misticraft.PESTLE) {

                Optional<MortarRecipe> match = world.getRecipeManager().getFirstMatch(MortarRecipe.Type.INSTANCE, mbe, world);
                if(curState.get(COOLDOWN) == 0 && match.isPresent()) {
                    player.playSound(SoundEvents.BLOCK_GRINDSTONE_USE, 1, 1);
                    world.setBlockState(pos, curState.with(COOLDOWN, COOLDOWN_TIME));
                    player.getInventory().offerOrDrop(match.get().getOutput().copy());
                    mbe.getStack(0).decrement(1);
                    world.updateListeners(pos, curState, world.getBlockState(pos), Block.NOTIFY_LISTENERS);
                    return ActionResult.SUCCESS;
                }
            }else{
                if(mbe.getStack(0).isEmpty()){
                    ItemStack is = player.getStackInHand(hand);
                    mbe.setStack(0, is);
                    player.setStackInHand(hand, ItemStack.EMPTY);
                }else{
                    player.getInventory().offerOrDrop(mbe.getStack(0).copy());
                    mbe.setStack(0, ItemStack.EMPTY);
                }
                world.updateListeners(pos, curState, world.getBlockState(pos), Block.NOTIFY_LISTENERS);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Misticraft.MORTAR_BET, (world1, pos1, state1, be) -> MortarBlockEntity.tick(world1, pos1, state1, be));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COOLDOWN);
    }
}
