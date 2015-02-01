package meew0.healthmod.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

/**
 * Created by meew0 on 01.02.15.
 */
public class GuiSymptomOverlay extends Gui {
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        this.drawString(Minecraft.getMinecraft().fontRenderer, "asdfhjkasdh", 0, 0, 0);
    }
}
