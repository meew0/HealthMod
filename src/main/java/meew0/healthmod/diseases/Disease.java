package meew0.healthmod.diseases;

import meew0.healthmod.symptoms.AmplifiedSymptom;

/**
 * Created by meew0 on 24.01.15.
 */
public abstract class Disease {
    public abstract AmplifiedSymptom[] getSymptomsForStage(int stage);
}
