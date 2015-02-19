package meew0.healthmod.items;

import meew0.healthmod.properties.PlayerBodyProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by meew0 on 05.02.15.
 */
public class ItemThermometer extends Item {
    private static String[] textures = new String[] { "healthmod:thermometer_blood", "healthmod:thermometer_aural",
                                    "healthmod:thermometer_rectal" },
                            unlocalizedTypeNames = new String[] { "healthmod.msg.thermometerBlood",
                                    "healthmod.msg.thermometerAural", "healthmod.msg.thermometerRectal"};

    private static final float degradationClean = 0.f;
    private static final float degradationSlightlyDirty = 3.f;
    private static final float degradationDirty = 4.f;
    private static final float degradationFilthy = 5.f;
    private static final float degradationContaminated = 7.f;
    private static final float degradationInfected = 10.f;

    private static List<Function<Float, Float>> degradationFuncs = new ArrayList<>(), accuracyFuncs = new ArrayList<>(),
                                                conversionFuncs = new ArrayList<>();

    static {
        degradationFuncs.add(/* Blood  */ old -> 0.f);
        degradationFuncs.add(/* Aural  */ old -> old + .4f);
        degradationFuncs.add(/* Rectal */ old -> old * 1.2f);

        accuracyFuncs.add(/* Blood  */ deg -> .3f);
        accuracyFuncs.add(/* Aural  */ deg -> (float)(.1f*Math.pow(deg, 1.8f) + 1.f));
        accuracyFuncs.add(/* Rectal */ deg -> (float)(.15f*Math.pow(deg, 1.3f) + .5f));

        conversionFuncs.add(/* K to °C  */ deg -> deg - 273.15f);
        conversionFuncs.add(/* K to K   */ deg -> deg);
        conversionFuncs.add(/* K to °F  */ deg -> deg * (9.f/5.f) - 459.67f);
        conversionFuncs.add(/* K to °Ra */ deg -> deg * (9.f/5.f));
        conversionFuncs.add(/* K to °D  */ deg -> (373.15f - deg) * (3.f/2.f));
        conversionFuncs.add(/* K to °N  */ deg -> (deg - 273.15f) * (33.f/100.f));
        conversionFuncs.add(/* K to °Ré */ deg -> (deg - 273.15f) * (4.f/5.f));
        conversionFuncs.add(/* K to °Rø */ deg -> (deg - 273.15f) * (21.f/40.f) + 7.5f);
    }


    public ItemThermometer() {
        super();
        this.setHasSubtypes(true);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
        if(!stack.stackTagCompound.hasKey("Degradation")) stack.stackTagCompound.setFloat("Degradation", 0.f);
        if(!stack.stackTagCompound.hasKey("PreferredUnit")) stack.stackTagCompound.setInteger("PreferredUnit", 0);
    }

    public IChatComponent formatBodyTemperature(float temp, float tempK, int preferredUnit) {
        String unitAbbr = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.unit" + preferredUnit +
                ".abbr");

        // Insane ternary operator gore
        String temperatureRange =
                (tempK < 308.65f)                           ? "SuperBad"    : (
                (tempK >= 308.65f && tempK < 309.65f)       ? "Bad"         : (
                (tempK >= 309.65f && tempK < 310.05f)       ? "NotIdeal"    : (
                (tempK >= 310.05f && tempK < 310.65f)       ? "Ideal"       : (
                (tempK >= 310.65f && tempK < 311.15f)       ? "NotIdeal"    : (
                (tempK >= 311.15f && tempK < 312.65f)       ? "Bad"         : (
                (tempK >= 312.65f)                          ? "SuperBad"    : (
                                                              "SuperBad")))))));
        String temperature = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.formatting" +
                temperatureRange).replace("%n", "" + temp).replace("%u", unitAbbr);

        String message = StatCollector.translateToLocal("healthmod.msg.bodyTemperature").replace("%n", temperature);

        return new ChatComponentText(message);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
                             int side, float dx, float dy, float dz) {
        // Degrade
        float oldDegradation = stack.stackTagCompound.getFloat("Degradation");
        float newDegradation = degradationFuncs.get(stack.getItemDamage()).apply(oldDegradation);
        stack.stackTagCompound.setFloat("Degradation", newDegradation);

        // Get temperature
        float temperature = PlayerBodyProperties.getForPlayer(player).bodyTemperature;

        // Get accuracy
        // Use old degradation to degrade item as it should technically degrade after item is used
        float accuracy = accuracyFuncs.get(stack.getItemDamage()).apply(oldDegradation);
        // Get random modifier ranging from -accuracy to +accuracy
        float accuracyBasedModifier = Math.round(itemRand.nextFloat() * 2.f * accuracy) - accuracy;
        // Round ABM to two decimal places
        float roundedABM = ((float) Math.round(accuracyBasedModifier * 100.f)) / 100.f;
        // Apply to temperature
        float modifiedTemperature = temperature + accuracyBasedModifier;

        // Convert to preferred unit
        int preferredUnit = stack.stackTagCompound.getInteger("PreferredUnit");
        float convertedTemperature = conversionFuncs.get(preferredUnit).apply(modifiedTemperature);

        // Output to chat
        player.addChatComponentMessage(new ChatComponentText());
    }
}
