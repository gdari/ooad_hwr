package ru.nsu.ccfit.hwr.logic.recognition.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Баира on 21.12.13.
 */
public class StrokesGroup {
    private Baseline baseline;
    private final BoundingBox bb;
    private List<Stroke> strokes = new ArrayList<Stroke>();
    private int pointsCount = 0;

    public StrokesGroup(StrokesGroup other) {
        this.baseline = other.baseline;
        this.bb = new BoundingBox(other.bb);
        this.strokes = new ArrayList<Stroke>(other.strokes);
        this.pointsCount = other.pointsCount;
    }

    public StrokesGroup(Stroke stroke) {
        bb = new BoundingBox(stroke);
        this.baseline = Baseline.MAIN_SCRIPT;
        strokes.add(stroke);
        this.pointsCount+=stroke.size();
    }

    public void addStroke(Stroke stroke) {
        strokes.add(stroke);
        bb.merge(stroke.getBoundingBox());
        this.pointsCount+=stroke.size();
    }

    public void setBaseline(Baseline b) {
        this.baseline = b;
    }

    public Stroke getStroke(int idx) {
        return strokes.get(idx);
    }

    public int size() {
        return strokes.size();
    }

    public Stroke getOneBigStroke() {
        PointsContainer pointsContainer = new PointsContainer();
        for (Stroke s : strokes) {
            pointsContainer.merge(s);
        }
        return new Stroke(pointsContainer);
    }

    public Baseline getBaseline() {
        return baseline;
    }

    public Point getCenterPoint() {
        return bb.centerPoint;
    }

    public BoundingBox getBoundingBox() {
        return bb;
    }

    public List<Stroke> getStrokes() {
        return strokes;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    /*TODO : why stroke ??? wtf*/
    public void merge(Stroke strokesGroup) {
        addStroke(strokesGroup);
    }
}
