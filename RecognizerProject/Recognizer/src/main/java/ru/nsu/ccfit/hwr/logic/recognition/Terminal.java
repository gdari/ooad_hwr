package ru.nsu.ccfit.hwr.logic.recognition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Баира on 21.12.13.
 */
public class Terminal implements Serializable{
    private final String label;
    final List<FeatureVector> featureVectorList; /* различные pattern -ы*/

    public Terminal(String label, FeatureVector fv) {
        featureVectorList = new ArrayList<FeatureVector>();
        featureVectorList.add(fv);
        this.label = label;
    }

    public void addFeatureVector(FeatureVector fv) {
        this.featureVectorList.add(fv);
    }

    public List<FeatureVector> getFeatureVectorList(){
        return featureVectorList;
    }

    public String getLabel() {
        return label;
    }
}
