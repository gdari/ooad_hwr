package ru.nsu.ccfit.hwr.activity;


/**
 * Created by Баира on 21.12.13.
 */

import java.util.ArrayList;
import java.util.List;

import ru.nsu.ccfit.hwr.logic.recognition.data.PointsContainer;

public class Drawing {
    static private Drawing drawing;
	
    static public Drawing getInstance() {
        if (null == drawing) {
            drawing = new Drawing();
        }
        return drawing;
    }
    private Drawing () {}
    
	private List<PointsContainer> pointsContainers = new ArrayList<PointsContainer>();

    public void add(PointsContainer p) {
        pointsContainers.add(p);
    }
	
	public List<PointsContainer> getPoints() {
		return pointsContainers;
	}
	
	public void clear() {
		pointsContainers = new ArrayList<PointsContainer>();
	}
	
	public boolean cancel() {
		if (pointsContainers.isEmpty()) return false;
		pointsContainers.remove(pointsContainers.size()-1);
		return true;
	}

    public int size() {
        return pointsContainers.size();
    }
}
