package meew0.healthmod.symptoms;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

/**
 * Created by meew0 on 24.01.15.
 */
public class SymptomFever extends Symptom {
    @Override
    public void performSymptomEffect(EntityPlayer player, int amplifier) {
        //for debugging
        player.addChatComponentMessage(new ChatComponentText("Hey! You have a fever!"));
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
