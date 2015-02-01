package meew0.healthmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import meew0.healthmod.properties.PlayerSymptoms;
import meew0.healthmod.symptoms.AmplifiedSymptom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;

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

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if(event.entity instanceof EntityPlayer && PlayerSymptoms.getForPlayer((EntityPlayer) event.entity) == null) {
            PlayerSymptoms.addToPlayer((EntityPlayer) event.entity);
        }
    }
}
