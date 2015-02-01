package meew0.healthmod.symptoms;

import meew0.healthmod.properties.PlayerDiseases;
import meew0.healthmod.properties.PlayerSymptoms;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by meew0 on 24.01.15.
 */
public abstract class Symptom {
    public void performEffect(EntityPlayer entity, int amplifier) {
        if(shouldSymptomRunOut(entity, amplifier)) {
            PlayerSymptoms.getForPlayer(entity).removeSymptom(this);
        }

        // Now perform the actual effect
        performSymptomEffect(entity, amplifier);
    }

    public abstract void performSymptomEffect(EntityPlayer player, int amplifier);
    public abstract String getUniqueSymptomID();
    public abstract int getDisplayColor();

    public AmplifiedSymptom instantiate(int amplifier) {
        return new AmplifiedSymptom(this, amplifier);
    }

    public boolean shouldSymptomRunOut(EntityPlayer entity, int amplifier) {
        // By default, only let a symptom run out if the player still has it according to its disease profile
        // Subclasses can overwrite this method if they want lingering symptoms
        return !PlayerDiseases.doesPlayerHaveSymptom(entity, this);
    }
}
