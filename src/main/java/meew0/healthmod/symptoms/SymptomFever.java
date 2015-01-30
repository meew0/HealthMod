package meew0.healthmod.symptoms;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by meew0 on 24.01.15.
 */
public class SymptomFever extends Symptom {
    protected SymptomFever(int id, boolean isBadEffect, int particleColor) {
        super(id, isBadEffect, particleColor);
    }

    @Override
    public void performSymptomEffect(EntityPlayer player, int amplifier) {

    }
}
