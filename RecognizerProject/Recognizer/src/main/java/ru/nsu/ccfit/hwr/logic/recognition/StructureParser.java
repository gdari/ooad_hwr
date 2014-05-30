package ru.nsu.ccfit.hwr.logic.recognition;

import java.util.List;

import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.recognition.data.RecognizedCharacter;

/**
 * Created by Баира on 21.12.13.
 */
public interface StructureParser {
    public SEquation parse(List<RecognizedCharacter> characters);

}
