package ru.nsu.ccfit.hwr.logic.recognition.data;

/**
 * Created by Баира on 21.12.13.
 */
public class RecognizedCharacter extends StrokesGroup {
    private float penalty = 10;
    private final String label;

    public RecognizedCharacter(StrokesGroup strokesGroup, String label, float penalty){
        super(strokesGroup);
        this.label = label;
        this.penalty = penalty;
    }

    public float getPenalty() {
        return penalty;
    }

    public String getLabel() {
        return label;
    }
}
