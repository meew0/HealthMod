package meew0.healthmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import meew0.healthmod.client.GuiSymptomOverlay;
import meew0.healthmod.properties.PlayerBodyProperties;
import meew0.healthmod.properties.PlayerDiseases;
import meew0.healthmod.properties.PlayerSymptoms;
import meew0.healthmod.symptoms.AmplifiedSymptom;
import meew0.healthmod.symptoms.SymptomFever;
import meew0.healthmod.symptoms.SymptomNausea;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityEvent;

/**
 * Created by meew0 on 01.02.15.
 */
public class HealthModEventHandler {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Only perform the effect in survival/adventure mode
        if(!event.player.capabilities.isCreativeMode) {
            PlayerSymptoms symptoms = PlayerSymptoms.getForPlayer(event.player);
            if (symptoms == null) return;
            for (AmplifiedSymptom s : symptoms.symptoms) {
                s.symptom.performSymptomEffect(event.player, s.amplifier);
            }
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if(event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if(PlayerSymptoms.getForPlayer(player) == null) {
                PlayerSymptoms.addToPlayer(player);

                HealthMod.debug("Adding symptoms");
                PlayerSymptoms.getForPlayer(player).addSymptom(new SymptomFever().instantiate(0));
                PlayerSymptoms.getForPlayer(player).addSymptom(new SymptomNausea().instantiate(2));
            }

            if(PlayerBodyProperties.getForPlayer(player) == null) {
                PlayerBodyProperties.addToPlayer(player);
            }

            if(PlayerDiseases.getForPlayer(player) == null) {
                PlayerDiseases.addToPlayer(player);
            }
        }
    }

    @SubscribeEvent
    public void onTextureStitchPre(TextureStitchEvent.Pre event) {
        GuiSymptomOverlay.symptomOverlay = event.map.registerIcon("healthmod:overlay_symptom");
    }
}
