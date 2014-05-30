package ru.nsu.ccfit.hwr.logic.recognition;
import java.util.List;

import ru.nsu.ccfit.hwr.recognition.R;
import ru.nsu.ccfit.hwr.logic.recognition.data.RecognizedCharacter;
import ru.nsu.ccfit.hwr.logic.recognition.data.StrokesGroup;

/**
 * Created by Баира on 21.12.13.
 */
public interface Classifier {
    public static final int PENALTY_THRESHOLD = R.integer.penalty_threshold;

    public void train(TrainingSet trainingSet);
    public List<RecognizedCharacter> classify(StrokesGroup raw);
}
