package mod.francescozucca.misticraft.block.entity;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.block.Candle;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CandleEntity extends BlockEntity {

    private static final int DURATION = (5*60*20)/8;
    private int current_duration;
    public CandleEntity(BlockPos pos, BlockState state) {
        super(Misticraft.CANDLE_BET, pos, state);
        current_duration = 0;
    }

    public static void tick(World world, BlockPos pos, BlockState state, CandleEntity entity){
        if(!world.isClient) {

            entity.current_duration++;

            if (entity.current_duration >= DURATION) {
                if (state.get(Candle.CONSUMED) == 7)
                    world.breakBlock(pos, false);
                else {
                    world.setBlockState(pos, state.with(Candle.CONSUMED, state.get(Candle.CONSUMED) + 1));
                    entity.current_duration = 0;
                }
            }
            ((Candle) (state.getBlock())).applyEffect(world, pos);
        }else{
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

            world.addParticle(ParticleTypes.FLAME, 0.5, py, 0.5, 0, -0.1, 0);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        current_duration = nbt.getInt("current_duration");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("current_duration", current_duration);
    }
}
