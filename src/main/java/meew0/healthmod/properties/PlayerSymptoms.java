package meew0.healthmod.properties;

import meew0.healthmod.symptoms.Symptom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meew0 on 31.01.15.
 */
public class PlayerSymptoms implements IExtendedEntityProperties {
    public static final String propertyName = "HealthMod_playerSymptoms";

    private final EntityPlayer player;
    private List<Symptom> symptoms;

    public PlayerSymptoms(EntityPlayer player) {
        this.player = player;
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
        //TODO
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {

    }

    @Override
    public void init(Entity entity, World world) {
        this.symptoms = new ArrayList<Symptom>();
    }
}
