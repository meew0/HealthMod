package meew0.healthmod.properties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by meew0 on 04.02.15.
 */
public class PlayerBodyProperties implements IExtendedEntityProperties {
    public static final String propertyName = "HealthMod_playerBodyProperties";

    private final EntityPlayer player;
    public float bodyTemperature = 37.3f;

    public PlayerBodyProperties(EntityPlayer player) {
        this.player = player;
    }

    public static void addToPlayer(EntityPlayer player) {
        player.registerExtendedProperties(propertyName, new PlayerBodyProperties(player));
    }

    public static PlayerBodyProperties getForPlayer(EntityPlayer player) {
        return (PlayerBodyProperties) player.getExtendedProperties(propertyName);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setFloat("BodyTemperature", bodyTemperature);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        bodyTemperature = compound.getFloat("BodyTemperature");
    }

    @Override
    public void init(Entity entity, World world) {

    }
}
