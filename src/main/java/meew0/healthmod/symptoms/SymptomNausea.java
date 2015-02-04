package meew0.healthmod.symptoms;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Created by meew0 on 04.02.15.
 */
public class SymptomNausea extends SymptomPotionEffect {

    @Override
    public PotionEffect getPotionEffect(EntityPlayer player, int amplifier) {
        return new PotionEffect(Potion.confusion.getId(), 200, amplifier);
    }

    @Override
    public String getUniqueSymptomID() {
        return "Nausea";
    }

    @Override
    public int getDisplayColor() {
        return 0x00e84c;
    }
}
