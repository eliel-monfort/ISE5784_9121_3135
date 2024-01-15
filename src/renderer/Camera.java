package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera implements Cloneable {
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width = 0.0;
    private double height = 0.0;
    private double distance = 0.0;

    private Camera(){}

    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }

    public Builder getBuilder(){
        return null;
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        Point PC = p0.add(vTo.scale(distance));

        double Rx = width/nX;
        double Ry = height/nX;

        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        Point Pij = PC;
        if (Xj != 0){
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (Yi != 0){
            Pij = Pij.add(vUp.scale(Yi));
        }

        return new Ray(p0, Pij.subtract(p0));
    }

    public static class Builder(){
        final private Camera camera = new Camera();

        Builder setLocation(Point _p0){
            this.camera.p0 = _p0;
            return this;
        }

        Builder setDirection(Vector _vTo, Vector _vUp){
            if (!isZero(_vTo.dotProduct(_vUp))){
                throw new IllegalArgumentException("The two vectors (parameters) are not orthogonal");
            }
            this.camera.vTo = _vTo.normalize();
            this.camera.vUp = _vUp.normalize();
            return this;
        }

        Builder setVpSize(double _width, double _height){
            this.camera.width = _width;
            this.camera.height = _height;
            return this;
        }

        Builder setVpDistance(double _distance){
            this.camera.distance = _distance;
            return this;
        }

        public Camera build(){
            // TODO: continue...
            return null;
        }
    }
}
