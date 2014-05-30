package ru.nsu.ccfit.hwr.logic.recognition;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Баира on 21.12.13.
 */
public class TrainingSet implements Serializable{
    private final Map<String, Terminal> terminalMap = new HashMap<String, Terminal>();

    public void addPattern(String label, FeatureVector fv){
        Terminal terminal = this.terminalMap.get(label);
        if (null == terminal) {
            this.terminalMap.put(label, new Terminal(label, fv));
        } else {
            terminal.addFeatureVector(fv);
        }

       // Log.i("addPattern training set", fv.toString());

    }

    public Terminal getTerminal(String label) {
        return  terminalMap.get(label);
    }

    public Collection<Terminal> getTerminals() {
        return terminalMap.values();
    }

    public int getSize() {
        return terminalMap.size();
    }

    public Set<String> getLabels() {
        return terminalMap.keySet();
    }

    public void clear() {
        terminalMap.clear();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Terminal t : terminalMap.values()) {
            stringBuilder.append(t.getLabel());
            stringBuilder.append(": ");
            stringBuilder.append(t.featureVectorList.size());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
