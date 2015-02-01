package meew0.healthmod.properties;

import meew0.healthmod.symptoms.AmplifiedSymptom;
import meew0.healthmod.symptoms.Symptom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meew0 on 31.01.15.
 */
public class PlayerSymptoms implements IExtendedEntityProperties {
    public static final String propertyName = "HealthMod_playerSymptoms";

    private final EntityPlayer player;
    public List<AmplifiedSymptom> symptoms;

    public PlayerSymptoms(EntityPlayer player) {
        this.player = player;
        this.symptoms = new ArrayList<AmplifiedSymptom>();
    }

    public static void addToPlayer(EntityPlayer player) {
        player.registerExtendedProperties(propertyName, new PlayerSymptoms(player));
    }

    public static PlayerSymptoms getForPlayer(EntityPlayer player) {
        return (PlayerSymptoms) player.getExtendedProperties(propertyName);
    }

    public void removeSymptom(Symptom s) {
        //TODO
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for(AmplifiedSymptom as : symptoms) {
            list.appendTag(as.serializeToNBT());
        }
        compound.setTag("Symptoms", list);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.symptoms = new ArrayList<AmplifiedSymptom>();
        NBTTagList list = compound.getTagList("Symptoms", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            symptoms.add(new AmplifiedSymptom(tag));
        }
    }

    @Override
    public void init(Entity entity, World world) {
    }
}
