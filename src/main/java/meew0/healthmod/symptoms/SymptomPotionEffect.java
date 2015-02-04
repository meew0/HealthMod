package meew0.healthmod.symptoms;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

/**
 * Created by meew0 on 04.02.15.
 */
public abstract class SymptomPotionEffect extends Symptom {
    @Override
    public void performSymptomEffect(EntityPlayer player, int amplifier) {
        player.addPotionEffect(getPotionEffect(player, amplifier));
    }

    public abstract PotionEffect getPotionEffect(EntityPlayer player, int amplifier);
}
