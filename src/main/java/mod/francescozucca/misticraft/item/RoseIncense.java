package mod.francescozucca.misticraft.item;

import mod.francescozucca.misticraft.Misticraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class RoseIncense extends Incense{
    public RoseIncense(Settings settings) {
        super(settings);
    }

    @Override
    public void applyEffect(LivingEntity entity, World world) {
        entity.addStatusEffect(new StatusEffectInstance(Misticraft.ROSE_INCENSE_SE, 5, 1));
    }

    @Override
    public Identifier getParticleID() {
        return Misticraft.id("rose_incense");
    }

    @Override
    public int getDuration() {
        return 60*20;
    }
}
