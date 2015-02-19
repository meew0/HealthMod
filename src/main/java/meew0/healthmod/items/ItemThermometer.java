package meew0.healthmod.items;

import meew0.healthmod.properties.PlayerBodyProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
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

    private static List<Function<Float, Float>> degradationFuncs = new ArrayList<>(), accuracyFuncs = new ArrayList<>(),
                                                conversionFuncs = new ArrayList<>(),
                                                deltaConversionFuncs = new ArrayList<>();

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

        deltaConversionFuncs.add(/* K to °C  */ deg -> deg);
        deltaConversionFuncs.add(/* K to K   */ deg -> deg);
        deltaConversionFuncs.add(/* K to °F  */ deg -> deg * (9.f/5.f));
        deltaConversionFuncs.add(/* K to °Ra */ deg -> deg * (9.f/5.f));
        deltaConversionFuncs.add(/* K to °D  */ deg -> deg * (3.f/2.f));
        deltaConversionFuncs.add(/* K to °N  */ deg -> deg * (33.f/100.f));
        deltaConversionFuncs.add(/* K to °Ré */ deg -> deg * (4.f/5.f));
        deltaConversionFuncs.add(/* K to °Rø */ deg -> deg * (21.f/40.f));
    }


    public ItemThermometer() {
        super();
        this.setHasSubtypes(true);
    }

    /**
     * Round something to two decimal places
     */
    private static float round2d(float f) {
        return ((float) Math.round(f*100.f))/100.f;
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
    public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean advancedTooltips) {
        float degradation = stack.stackTagCompound.getFloat("Degradation");
        int roundedDegradation = (int) Math.floor(degradation);
        String formattedDegradation = StatCollector.translateToLocal("healthmod.degradation." + roundedDegradation);
        String degradationMsg = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.degradation")
                .replace("%d", formattedDegradation);

        float accuracy = round2d(accuracyFuncs.get(stack.getItemDamage()).apply(degradation));
        String accuracyMsg = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.accuracy")
                .replace("%a", "" + accuracy);

        int unit = stack.stackTagCompound.getInteger("PreferredUnit");
        String unitFull = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.unit" + unit +
                ".full"), unitAbbr = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.unit" +
                unit + ".abbr");

        String unitMsg = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.currentUnit")
                .replace("%u", unitFull).replace("%a", unitAbbr);

        String usage0 = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.usage.0"),
                usage1 = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.usage.1");

        information.add(degradationMsg);
        information.add(accuracyMsg);
        information.add(unitMsg);
        information.add("");
        information.add(usage0);
        information.add(usage1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
                             int side, float dx, float dy, float dz) {
        if(player.isSneaking()) {
            // Cycle through units
            int currentUnit = stack.stackTagCompound.getInteger("PreferredUnit");
            int newUnit = (currentUnit + 1) % 8;
            stack.stackTagCompound.setInteger("PreferredUnit", newUnit);

            // Output to chat
            String newUnitFull = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.unit" + newUnit +
                    ".full"), newUnitAbbr = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.unit" +
                    newUnit + ".abbr");

            String msg = StatCollector.translateToLocal("healthmod.msg.bodyTemperature.unitSwitched")
                    .replace("%u", newUnitFull).replace("%a", newUnitAbbr);

            player.addChatComponentMessage(new ChatComponentText(msg));
        } else {
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
            float accuracyBasedModifier = (itemRand.nextFloat() * 2.f * accuracy) - accuracy;
            // Apply to temperature
            float modifiedTemperature = temperature + accuracyBasedModifier;

            // Convert to preferred unit
            int preferredUnit = stack.stackTagCompound.getInteger("PreferredUnit");
            float convertedTemperature = conversionFuncs.get(preferredUnit).apply(modifiedTemperature);

            // Output to chat
            player.addChatComponentMessage(formatBodyTemperature(round2d(convertedTemperature),
                    round2d(modifiedTemperature), preferredUnit));
        }
        return true;
    }
}
