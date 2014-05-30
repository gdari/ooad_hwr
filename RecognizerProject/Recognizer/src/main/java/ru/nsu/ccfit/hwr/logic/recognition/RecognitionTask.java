package ru.nsu.ccfit.hwr.logic.recognition;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.recognition.data.PointsContainer;
import ru.nsu.ccfit.hwr.logic.recognition.data.RecognizedCharacter;
import ru.nsu.ccfit.hwr.logic.recognition.data.Stroke;
import ru.nsu.ccfit.hwr.logic.recognition.data.StrokesGroup;
import ru.nsu.ccfit.hwr.logic.recognition.kNNClassifier.KNNClassifier;
import ru.nsu.ccfit.hwr.recognition.R;
import ru.nsu.ccfit.hwr.logic.recognition.twoDParser.TwoDParser;

/**
 * Created by Баира on 21.12.13.
 */
public class RecognitionTask {
    static private RecognitionTask recognitionTask = null;
    static public RecognitionTask getInstance(Context context) {
        if (null == recognitionTask) {
            recognitionTask = new RecognitionTask(context);
        }
        return recognitionTask;
    }

    private final Classifier classifier;

    private RecognitionTask(Context context){
        classifier = new KNNClassifier(context.getResources().getInteger(R.integer.k_neighbours),  context.getResources().getInteger(R.integer.pattern_size) , context.getResources().getInteger(R.integer.points_count));

        StructureParser structureParser = new TwoDParser();

        TrainingSetReader reader = new TrainingSetDBReader(context);
        classifier.train(reader.readTrainingSet());
    }

    public List<Stroke> segment(List<PointsContainer> pointsContainers) {
        List<Stroke> strokes = new ArrayList<Stroke>(pointsContainers.size());
        for (PointsContainer pointsContainer : pointsContainers) {
            strokes.add(new Stroke(pointsContainer));
        }
        return strokes;
    }

    public SEquation recognize(List<Stroke> raw){
        /*TODO : just test*/
        /*todo : strokes group 1-4*/
        StringBuilder stringBuilder = new StringBuilder();
        int count;
        for (int i = 0; i < raw.size(); i+=count) {
            StrokesGroup firstStrokesGroup = new StrokesGroup(raw.get(i));
            List<RecognizedCharacter> list1 = classifier.classify(firstStrokesGroup);
            List<RecognizedCharacter> list = list1;
            float p = list.get(0).getPenalty();
            count = 1;
            //TODO:
            if (i+1 < raw.size() ) {
                firstStrokesGroup.addStroke(raw.get(i+1));
                List<RecognizedCharacter> list2 = classifier.classify(new StrokesGroup(firstStrokesGroup));
                float p2 = list2.get(0).getPenalty();
                if (p2 < p || raw.get(i).isCrossing(raw.get(i+1))) {
                    list = list2;
                    count = 2;
                    p = p2;
                }
                if (i+2 < raw.size() ) {
                    firstStrokesGroup.addStroke(raw.get(i+2));
                    List<RecognizedCharacter> list3 = classifier.classify(new StrokesGroup(firstStrokesGroup));
                    float p3 = list3.get(0).getPenalty();
                    if (p3 < p || raw.get(i).isCrossing(raw.get(i+2))) {
                        list = list3;
                        count = 3;
                    }
                }
            }
            // " " for dari
            if (list.isEmpty()) continue;
            stringBuilder.append(list.get(0).getLabel() + " ");
        }
        return new SEquation(stringBuilder.toString());
    }


}
