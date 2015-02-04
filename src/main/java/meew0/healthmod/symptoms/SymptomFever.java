package meew0.healthmod.symptoms;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by meew0 on 24.01.15.
 */
public class SymptomFever extends Symptom {
    @Override
    public void performSymptomEffect(EntityPlayer player, int amplifier) {
    }

    @Override
    public String getUniqueSymptomID() {
        return "Fever";
    }

    @Override
    public int getDisplayColor() {
        return 0xFF0000;
    }
}
