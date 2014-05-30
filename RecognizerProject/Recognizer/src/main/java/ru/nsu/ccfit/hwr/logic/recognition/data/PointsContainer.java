package ru.nsu.ccfit.hwr.logic.recognition.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Баира on 21.12.13.
 */
public class PointsContainer {
    private static final float epsilon = 10;
    private List<Point> points = new ArrayList<Point>();

    PointsContainer(PointsContainer p) {
        this.points = p.points;
    }
    public PointsContainer() {}

    public void addPoint(Point p) {
        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }
    // TODO : how to float[]
    public float[] getVectorX() {
        if (points.size() == 0) return null;
        int i = 0;
        float[] vectorX = new float[points.size()];
        for (Point p : points) {
            vectorX[i] = p.x;
            ++i;
        }
        return vectorX;
    }

    public float[] getVectorY() {
        if (points.size() == 0) return null;
        int i = 0;
        float[] vectorY = new float[points.size()];
        for (Point p : points) {
            vectorY[i] = p.y;
            ++i;
        }
        return vectorY;
    }

    public Point get(int idx) {
        if (idx >= size())
        return points.get(size()-1);
        return points.get(idx);
    }

    public int size() {
        return points.size();
    }

    public void trim() {

        if (points.size() <= 3) return;
        points = dpFunc(points, 0, points.size() );
    }

    public void merge(PointsContainer p) {
        this.points.addAll(p.points);
    }

    private static List<Point>  dpFunc(List<Point> points, int startIndex, int endIndex) {
        double dMax = 0;
        int idx = 0;
        for (int i = startIndex+1; i < endIndex; ++i) {
            PointsContainer line = new PointsContainer();
            line.addPoint(points.get(startIndex));
            line.addPoint(points.get(endIndex-1));
            double d = perpendicularDistanceToLine(points.get(i), line);
            if (d > dMax) {
                idx = i;
                dMax = d;
            }
        }

        if (dMax >= epsilon) {
            List<Point> recursResult1 = dpFunc(points, startIndex, idx);
            List<Point> recursResult2 = dpFunc(points, idx, endIndex);
            recursResult1.remove(recursResult1.size()-1);
            recursResult1.addAll(recursResult2);
            return recursResult1;

        } else {
            List<Point> line = new ArrayList<Point>();
            line.add(points.get(startIndex));
            line.add(points.get(endIndex-1));
            return line;
        }

    }

    private static float perpendicularDistanceToLine(Point a, PointsContainer line) {
        float p1 =  line.get(0).x;
        float p2 =  line.get(1).x;
        float q1 =  line.get(0).y;
        float q2 =  line.get(1).y;

        float dp = p1 - p2;
        float dq = q1 - q2;

        return (float)(  Math.abs(dq * a.x - dp * a.y + p1 * q2 - p2 * q1 ) / Math.sqrt(dp * dp + dq * dq) );
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PointsContainer : ");
        builder.append(points.size());
        builder.append("\n");
        for (Point p : points) {
            builder.append(p.toString());
            builder.append(" ");
        }
        builder.append("\n");

        return builder.toString();
    }

}
