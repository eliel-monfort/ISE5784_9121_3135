package geometries;

abstract public class RadialGeometry extends Geometry {
    protected double radius;

    public RadialGeometry(double _radius){
        this.radius = _radius;
    }
}
