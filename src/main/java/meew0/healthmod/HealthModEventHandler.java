package meew0.healthmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import meew0.healthmod.properties.PlayerSymptoms;
import meew0.healthmod.symptoms.AmplifiedSymptom;

/**
 * Created by meew0 on 01.02.15.
 */
public class HealthModEventHandler {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerSymptoms symptoms = PlayerSymptoms.getForPlayer(event.player);
        if(symptoms == null) return;
        for(AmplifiedSymptom s : symptoms.symptoms) {
            s.symptom.performSymptomEffect(event.player, s.amplifier);
        }
    }
}
