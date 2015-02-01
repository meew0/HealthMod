package meew0.healthmod.symptoms;

import meew0.healthmod.registry.SymptomRegistry;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by meew0 on 01.02.15.
 */
public class AmplifiedSymptom {
    public Symptom symptom;
    public int amplifier;

    AmplifiedSymptom(Symptom symptom, int amplifier) {
        this.symptom = symptom;
        this.amplifier = amplifier;
    }

    public AmplifiedSymptom(NBTTagCompound nbt) {
        this.symptom = SymptomRegistry.getSymptomForUID(nbt.getString("SymptomUID"));
        this.amplifier = nbt.getInteger("Amplifier");
    }

    public NBTTagCompound serializeToNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("Amplifier", amplifier);
        nbt.setString("SymptomUID", symptom.getUniqueSymptomID());
        return nbt;
    }
}
