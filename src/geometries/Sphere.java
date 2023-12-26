package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    private Point center;

    public Sphere(double _radius, Point _point){
        super(_radius);
        this.center = _point;
    }
    @Override
    public Vector getNormal(Point point_on_body) {
        return null;
    }
}
