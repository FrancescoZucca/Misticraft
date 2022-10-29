package mod.francescozucca.misticraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class Incense extends Item {
    public Incense(Settings settings) {
        super(settings);
    }

    public abstract void applyEffect(LivingEntity entity, World world);
    public abstract Identifier getParticleID();
    public abstract int getDuration();
}
