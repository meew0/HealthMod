package meew0.healthmod.symptoms;

import meew0.healthmod.diseases.PlayerDiseases;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Created by meew0 on 24.01.15.
 */
public abstract class Symptom extends Potion {
    protected Symptom(int id, boolean isBadEffect, int particleColor) {
        super(id, isBadEffect, particleColor);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        super.performEffect(entity, amplifier);

        // Don't do anything for non-players
        if(!(entity instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) entity;

        // Refresh effect unless the symptom should run out
        if(!shouldSymptomRunOut(player, amplifier)) {
            player.removePotionEffect(this.getId());
            player.addPotionEffect(new PotionEffect(this.getId(), 1, amplifier));
        }

        // Now perform the actual effect
        performSymptomEffect(player, amplifier);
    }

    public abstract void performSymptomEffect(EntityPlayer player, int amplifier);

    public boolean shouldSymptomRunOut(EntityPlayer entity, int amplifier) {
        // By default, only let a symptom run out if the player still has it according to its disease profile
        // Subclasses can overwrite this method if they want lingering symptoms
        return !PlayerDiseases.doesPlayerHaveSymptom(entity, this);
    }
}
