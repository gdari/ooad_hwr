package ru.nsu.ccfit.hwr.logic.recognition;

import java.io.IOException;

/**
 * Created by Баира on 21.12.13.
 */
public interface TrainingSetWriter {
    public void writeTrainingSet(TrainingSet resource)throws IOException;
}
