package mod.francescozucca.misticraft.blocks.entity;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.blocks.Mortar;
import mod.francescozucca.misticraft.util.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MortarBlockEntity extends BlockEntity implements ImplementedInventory {

    //TODO fix cooldown

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public MortarBlockEntity(BlockPos pos, BlockState state) {
        super(Misticraft.MORTAR_BET, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public static void tick(World world, BlockPos pos, BlockState state, MortarBlockEntity be) {
        int cooldown = state.get(Mortar.COOLDOWN);
        if(cooldown > 0){
            world.setBlockState(pos, state.with(Mortar.COOLDOWN, cooldown - 1));
        }else if (cooldown < 0){
            world.setBlockState(pos, state.with(Mortar.COOLDOWN, 0));
        }
    }
}
