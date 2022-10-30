package mod.francescozucca.misticraft.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RoseIncenseStatusEffect extends StatusEffect {
    public RoseIncenseStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFF9CA5);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % (100 >> amplifier) == 0 || duration == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.heal(.75f+(0.3f*amplifier));
    }
}
