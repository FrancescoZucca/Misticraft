package mod.francescozucca.misticraft.block;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.block.entity.CandleEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class Candle extends BlockWithEntity {

    public static final IntProperty CONSUMED = IntProperty.of("consumed", 0, 7);

    public Candle(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(CONSUMED, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CONSUMED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        double py = 1;
        switch (state.get(Candle.CONSUMED)) {
            case 0 -> py = 8d / 8d;
            case 1 -> py = 7d / 8d;
            case 2 -> py = 6d / 8d;
            case 3 -> py = 5d / 8d;
            case 4 -> py = 4d / 8d;
            case 5 -> py = 3d / 8d;
            case 6 -> py = 2d / 8d;
            case 7 -> py = 1d / 8d;
        }
        return VoxelShapes.cuboid(3d/8d, 0, 3d/8d, 6d/8d, py, 6d/8d);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CandleEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Misticraft.CANDLE_BET, CandleEntity::tick);
    }

    public abstract void applyEffect(World world, BlockPos pos);
}
