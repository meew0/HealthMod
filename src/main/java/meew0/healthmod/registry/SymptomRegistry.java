package meew0.healthmod.registry;

import meew0.healthmod.symptoms.Symptom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meew0 on 01.02.15.
 */
public class SymptomRegistry {
    public static List<Symptom> registeredSymptoms = new ArrayList<Symptom>();

    public static void registerSymptom(Symptom symptom) {
        registeredSymptoms.add(symptom);
    }

    public static Symptom getSymptomForUID(String symptomUid) {
        for(Symptom symptom : registeredSymptoms) {
            if(symptom.getUniqueSymptomID().equals(symptomUid)) return symptom;
        }
        return null;
    }
}
