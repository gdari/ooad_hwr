package ru.nsu.ccfit.hwr.logic.recognition;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Баира on 21.12.13.
 */
public class FeatureVector implements Serializable{
    private final float[] vector;
    public FeatureVector(float[] vector) {
        this.vector = vector;
    }

    public int getSize() {
        return vector.length;
    }

    public float getDistanceTo(FeatureVector other){
        float[] space = vector.length > other.getSize() ? vector : other.vector;
        float[] subspace = vector.length <= other.getSize() ? vector : other.vector;
        float dist = 0;
        for (int i = 0; i < subspace.length; ++i) {
            dist +=  Math.pow(vector[i] - other.vector[i], 2);
        }
        for (int i = subspace.length; i < space.length; ++i) {
            dist += Math.pow(space[i], 2);
        }
        Log.i("distance fv", "d " + dist);
        return (float) Math.sqrt(dist);
    }

    public float[] getVector(){
        return vector;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vector.length).append("\n:");
        for (float aVector : vector) {
            stringBuilder.append(aVector);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
