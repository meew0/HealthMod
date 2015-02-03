package meew0.healthmod.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import meew0.healthmod.properties.PlayerSymptoms;
import meew0.healthmod.symptoms.AmplifiedSymptom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.List;

/**
 * Created by meew0 on 01.02.15.
 */
public class GuiSymptomOverlay extends Gui {

    public static String getRomanNumeral(int num) {
        if (num >= 1000) return "M+";
        String ret = "";
        String n = String.valueOf(num);
        int l = n.length();
        if (l > 2) {
            switch (Integer.parseInt("" + n.charAt(l - 3))) {
                case 3:
                    ret += "C";
                case 2:
                    ret += "C";
                case 1:
                    ret += "C";
                    break;
                case 4:
                    ret += "C";
                case 5:
                    ret += "D";
                    break;
                case 6:
                    ret += "DC";
                    break;
                case 7:
                    ret += "DCC";
                    break;
                case 8:
                    ret += "DCCC";
                    break;
                case 9:
                    ret += "CM";
                    break;
            }
        }
        if (l > 1) {
            switch (Integer.parseInt("" + n.charAt(l - 2))) {
                case 3:
                    ret += "X";
                case 2:
                    ret += "X";
                case 1:
                    ret += "X";
                    break;
                case 4:
                    ret += "X";
                case 5:
                    ret += "L";
                    break;
                case 6:
                    ret += "LX";
                    break;
                case 7:
                    ret += "LXX";
                    break;
                case 8:
                    ret += "LXXX";
                    break;
                case 9:
                    ret += "XC";
                    break;
            }
        }
        if (l > 0) {
            switch (Integer.parseInt("" + n.charAt(l - 1))) {
                case 3:
                    ret += "I";
                case 2:
                    ret += "I";
                case 1:
                    ret += "I";
                    break;
                case 4:
                    ret += "I";
                case 5:
                    ret += "V";
                    break;
                case 6:
                    ret += "VI";
                    break;
                case 7:
                    ret += "VII";
                    break;
                case 8:
                    ret += "VIII";
                    break;
                case 9:
                    ret += "IX";
                    break;
            }
        }
        return ret;
    }

    private static final int symptomBoxHeight = 12;
    private static final int symptomBoxWidth = 100;
    private static final int symptomBoxPadding = 2;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        List<AmplifiedSymptom> symptoms = PlayerSymptoms.getForPlayer(Minecraft.getMinecraft().thePlayer).symptoms;

        int totalHeight = symptoms.size() * (symptomBoxHeight + symptomBoxPadding);
        int screenY = event.resolution.getScaledHeight() / 2 - totalHeight / 2;
        int screenX = event.resolution.getScaledWidth() - symptomBoxWidth - symptomBoxPadding;
        for(AmplifiedSymptom as : symptoms) {
            this.drawGradientRect(screenX, screenY, screenX + symptomBoxWidth, screenY + symptomBoxHeight, as.symptom.getDisplayColor(), as.symptom.getDisplayColor());
            this.drawString(Minecraft.getMinecraft().fontRenderer, as.getFullName(), screenX, screenY, as.symptom.getDisplayColor());
        }

    }
}
