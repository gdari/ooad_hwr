package ru.nsu.ccfit.hwr.logic.recognition.data;

/**
 * Created by Баира on 21.12.13.
 */

public final class BoundingBox {
    /*TODO : пока оставляем package доступ*/
    float height;
    float width;
    final Point leftDown;
    final Point rightUp;
    final Point centerPoint;
    private final Point centerPointSum;
    private int n ;

    public BoundingBox(BoundingBox b) {
        /*copy */
        this.height = b.height;
        this.width = b.width;
        this.leftDown = new Point(b.leftDown);
        this.rightUp = new Point(b.rightUp);
        this.centerPointSum = new Point(b.centerPointSum);
        this.centerPoint = new Point(b.centerPoint);
        this.n = b.n;
    }

    public BoundingBox(PointsContainer p) {
        float minX = Float.MAX_VALUE;
        float maxX = 0;
        float minY = minX;
        float maxY = 0;

        float centerPointX = 0;
        float centerPointY = 0;
        /* TODO : если что, то сделать оптимизацию */
        for (float x : p.getVectorX()) {
            if (x<minX) minX = x;
            if (x>maxX) maxX = x;
            centerPointX += x;
        }

        for (float y: p.getVectorY()) {
            if (y<minY) minY = y;
            if (y>maxY) maxY = y;
            centerPointY += y;
        }
        this.centerPointSum = new Point(centerPointX, centerPointY);

        this.n = p. size();

        centerPointX /= n;
        centerPointY /= n;

        this.width =  maxX  - minX ;
        this.height = maxY - minY;
        leftDown = new Point(minX, minY);
        rightUp = new Point(maxX, maxY);
        centerPoint = new Point(centerPointX, centerPointY);
    }

    public void merge(BoundingBox other) {
        /*Merge */
        if (this.leftDown.x > other.leftDown.x) {
            this.leftDown.x =  other.leftDown.x;
        }

        if (this.leftDown.y > other.leftDown.y) {
            this.leftDown.y =  other.leftDown.y;
        }

        if (this.rightUp.x < other.rightUp.x) {
            this.rightUp.x =  other.rightUp.x;
        }

        if (this.rightUp.y < other.rightUp.y) {
            this.rightUp.y =  other.rightUp.y;
        }
        width = rightUp.x - leftDown.x;
        height = rightUp.y - leftDown.y;

        centerPointSum.x += other.centerPointSum.x;
        centerPointSum.y += other.centerPointSum.y;
        this.n += other.n;
        centerPoint.x = centerPointSum.x / (this.n );
        centerPoint.y = centerPointSum.y / (this.n );
    }
}
