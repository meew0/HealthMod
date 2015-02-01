package meew0.healthmod.properties;

import meew0.healthmod.diseases.Disease;
import meew0.healthmod.symptoms.Symptom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by meew0 on 24.01.15.
 */
public class PlayerDiseases implements IExtendedEntityProperties {
    public static final String propertyName = "HealthMod_playerDiseases";


    public static boolean doesPlayerHaveSymptom(EntityPlayer player, Symptom s) {
        return true; //TODO
    }

    public static boolean doesPlayerHaveDisease(EntityPlayer player, Disease s) {
        return true; //TODO
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {

    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {

    }

    @Override
    public void init(Entity entity, World world) {

    }
}
