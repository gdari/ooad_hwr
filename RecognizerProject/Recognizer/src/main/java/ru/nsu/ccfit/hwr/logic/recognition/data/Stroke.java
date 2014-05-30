package ru.nsu.ccfit.hwr.logic.recognition.data;

/**
 * Created by Баира on 21.12.13.
 */
public class Stroke extends PointsContainer{
    private Baseline baseline;
    private final BoundingBox boundingBox;

    public Stroke(PointsContainer p) {
        super(p);
        boundingBox = new BoundingBox(p);
        /*значение по умолчанию baseline*/
        this.baseline = Baseline.MAIN_SCRIPT;
    }

    public void setBaseline(Baseline baseline) {
        this.baseline = baseline;
    }

    public Baseline getBaseline() {
        return baseline;
    }

    BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public float getWidth() {
        return boundingBox.width;
    }
    public float getHeight() {
        return boundingBox.height;
    }

    public Point getLeftDown() {
        return boundingBox.leftDown;
    }

    public Point getRightUp() {
        return boundingBox.rightUp;
    }

    public boolean isCrossing(Stroke another) {
        boolean b1 = this.boundingBox.leftDown.x <= another.boundingBox.leftDown.x &&
                    this.boundingBox.rightUp.x >= another.boundingBox.rightUp.x;
        // TODO : only +
        return b1;
    }
}
