package ru.nsu.ccfit.hwr.logic.recognition.twoDParser;

import java.util.List;

import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.recognition.StructureParser;
import ru.nsu.ccfit.hwr.logic.recognition.data.RecognizedCharacter;

/**
 * Created by Баира on 21.12.13.
 */
public class TwoDParser implements StructureParser {
    public SEquation parse(List<RecognizedCharacter> characters) {
        return  new SEquation(characters.get(0).getLabel());
    }
}
