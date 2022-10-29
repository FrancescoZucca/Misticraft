package mod.francescozucca.misticraft.block.entity;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.block.Burner;
import mod.francescozucca.misticraft.item.Incense;
import mod.francescozucca.misticraft.util.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import java.util.List;

public class BurnerBlockEntity extends BlockEntity implements ImplementedInventory {

    private int burnTicks;
    private Incense incenseType;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public BurnerBlockEntity(BlockPos pos, BlockState state) {
        super(Misticraft.BURNER_BET, pos, state);
        burnTicks = 0;
        incenseType = null;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public static void tick(World world, BlockPos pos, BlockState state, BurnerBlockEntity be){
        if(world.isClient()){
            if(world.getBlockState(pos).get(Burner.LIT) && ((int) world.getTime()) % 5 == 0)
                world.addParticle(Misticraft.ROSE_INCENSE_PT, pos.getX()+(9d/16d), pos.getY()+(10d/16d), pos.getZ()+(9d/16d), 0d, 0.1d, 0d);
            return;
        }

        if(world.getBlockState(pos).getBlock() != Misticraft.BURNER) return;

        boolean lit = world.getBlockState(pos).get(Burner.LIT);
        ItemStack stack = be.getStack(0);

        if(lit){
            be.burnTicks--;

            List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, Box.of(Vec3d.ofCenter(pos), 10d, 5d, 10d));

            for(LivingEntity e: list){
                if(be.incenseType!=null){
                    be.incenseType.applyEffect(e, world);
                }
            }

            if(be.burnTicks <= 0){
                world.setBlockState(pos, state.with(Burner.LIT, false));
                be.burnTicks = 0;
            }else if(!state.get(Burner.LIT)){
                world.setBlockState(pos, state.with(Burner.LIT, true));
            }
        }else{
            if(stack.getItem() instanceof Incense incense){
                stack.decrement(1);
                be.setStack(0, stack);

                be.burnTicks = incense.getDuration();
                be.incenseType = incense;
                world.setBlockState(pos, state.with(Burner.LIT, true));
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        nbt.putInt("burnTicks", burnTicks);
        nbt.putString("incense", Registry.ITEM.getId(incenseType).toString());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        burnTicks = nbt.getInt("burnTicks");
        incenseType = (Incense) Registry.ITEM.get(new Identifier(nbt.getString("incense")));
    }
}
