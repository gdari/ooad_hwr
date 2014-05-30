package ru.nsu.ccfit.hwr.logic.recognition.kNNClassifier;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.nsu.ccfit.hwr.logic.recognition.Classifier;
import ru.nsu.ccfit.hwr.logic.recognition.FeatureVector;
import ru.nsu.ccfit.hwr.logic.recognition.Terminal;
import ru.nsu.ccfit.hwr.logic.recognition.TrainingSet;
import ru.nsu.ccfit.hwr.logic.recognition.data.Point;
import ru.nsu.ccfit.hwr.logic.recognition.data.PointsContainer;
import ru.nsu.ccfit.hwr.logic.recognition.data.RecognizedCharacter;
import ru.nsu.ccfit.hwr.logic.recognition.data.Stroke;
import ru.nsu.ccfit.hwr.logic.recognition.data.StrokesGroup;


/**
 * Created by Баира on 21.12.13.
 */
public class KNNClassifier implements Classifier {
    private final int K ;
    private final int PATTERN_SIZE ;
    private final int N;
    private TrainingSet trainingSet;

    public KNNClassifier(int K, int patternSize, int pointsCount) {
        this.K = K;
        this.PATTERN_SIZE = patternSize;
        this.N = pointsCount;
    }

    public void train(TrainingSet trainingSet) {
        this.trainingSet = trainingSet;
        Log.i("TRAIN", trainingSet.toString());
    }

    public List<RecognizedCharacter> classify(StrokesGroup raw) {

        FeatureVector rawCharacter = extract(raw);
        List<Neighbour> distanceList = new ArrayList<Neighbour>(trainingSet.getSize());
        for (Terminal t : trainingSet.getTerminals()) {
            for (FeatureVector vector : t.getFeatureVectorList()) {
                float distance = rawCharacter.getDistanceTo(vector);
                distanceList.add(new Neighbour(t.getLabel(), distance));
            }
        }
        Collections.sort(distanceList, new Comparator<Neighbour>() {
            @Override
            public int compare(Neighbour neighbour, Neighbour neighbour2) {
                if (neighbour.distance < neighbour2.distance) return -1;
                if (neighbour2.distance < neighbour.distance) return 1;
                return 0;
            }
        });
        List<RecognizedCharacter> recognizedCharacterList = new ArrayList<RecognizedCharacter>();
        int count = K < distanceList.size() ? K : distanceList.size();

        for (int i = 0; i < count; ++i) {
            // TODO: одинаковые
            recognizedCharacterList.add(new RecognizedCharacter(raw, distanceList.get(i).label, distanceList.get(i).distance));
            Log.i("distn", distanceList.get(i).label + ": " + distanceList.get(i).distance);
        }
        return recognizedCharacterList;
    }

    public FeatureVector extract(StrokesGroup s) {
        ArrayList<Float> arrayList = new ArrayList<Float>();
        Stroke preparedStroke = prepare(s.getOneBigStroke());
        float[] vectorX = preparedStroke.getVectorX();
        float[] vectorY = preparedStroke.getVectorY();
        for (int i = 0; i < preparedStroke.getVectorX().length ; ++i) {
            arrayList.add(vectorX[i]);
            arrayList.add(vectorY[i]);
        }

        float[] vector = new float[arrayList.size()];
        for (int i = 0; i < vector.length; ++i) {
            vector[i] = arrayList.get(i);
        }

        return new FeatureVector(vector);
    }

    private Stroke prepare(Stroke s) {
        PointsContainer newPointsContainer = new PointsContainer();
        float length = 0;
        for (int j = 1; j < s.size(); ++j) {
            length += Point.getDistance(s.get(j), s.get(j - 1));
        }

        float l = length / N;
        Point tempPoint = new Point(s.get (0) );
        newPointsContainer.addPoint(new Point(s.get(0)));
        float temp = 0;
        int j = 0;
        boolean isNext = true;

        while (temp < length - l) {
            if (isNext) ++j;
            Point firstPoint = isNext ? s.get(j-1) : tempPoint;
            Point secondPoint = s.get (j);

            float x1 = firstPoint.x, x2 = secondPoint.x;
            float y1 = firstPoint.y, y2 = secondPoint.y;
            float d = Point.getDistance(firstPoint, secondPoint) ;


            if (x1 == x2 && y1 == y2) break;
            if ( d >= l )  {

                float newX= 0, newY = 0;
                if ( ! (x2 == x1 || y2 == y1) ) {
                    float k = (y2-y1) / (x2 - x1);
                    newX = Math.signum(x2-x1)*(float)Math.sqrt(l*l / (k*k+1)) ;
                    newY = newX * k;
                    newY = newY + firstPoint.y;
                    newX =  newX +firstPoint.x;
                }
                else if (x1 == x2){
                    newX = x1;
                    if (y2 > y1)
                        newY = y1+l;
                    else
                        newY = y1-l;
                }else if (y1 == y2) {
                    newY = y1;
                    if (x2 > x1)
                        newX = x1+l;
                    else
                        newX = x1-l;

                }
                tempPoint = new Point(newX, newY);

                isNext = d == l;
                newPointsContainer.addPoint(new Point(newX, newY));
                temp+= l;
                l = length/N;
            }
            else {
                temp += d;
                l -= d;
                isNext = true;
            }
        }
        newPointsContainer.addPoint(s.get(s.size() - 1));

        Stroke tempStroke = new Stroke(newPointsContainer);
        float k = tempStroke.getWidth() / tempStroke.getHeight();
        float dw = this.PATTERN_SIZE;
        float dh = this.PATTERN_SIZE;

        Point offset = new Point(0.f, 0.f);
        if (k > 1) {
            // w > h
            dh = dw / k;
            offset.y  = ( Math.abs((this.PATTERN_SIZE - dh ))/2);
        }
        else if (k < 1) {
            dw = dh * k;
            offset.x =  (Math.abs((this.PATTERN_SIZE - dw))/2);
        }
        float dk = dw/tempStroke.getWidth();
        PointsContainer returnContainer= new PointsContainer();
        for (Point point : tempStroke.getPoints()) {
            point  = new Point( ((point.x - tempStroke.getLeftDown().x) * dk + offset.x), ((point.y - tempStroke.getLeftDown().y) * dk + offset.y));
            returnContainer.addPoint(point);
        }
        //Log.i("tempStroke", tempStroke.toString());
        return new Stroke(returnContainer);
    }

    class Neighbour  {
        private final float distance;
        private final String label;

        Neighbour(String l, float d) {
            this.distance = d;
            this.label = l;
        }
    }
}
