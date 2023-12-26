package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    private double height;

    public Cylinder(double _radius, Ray _axus, double _height){
        super(_radius, _axus);
        height = _height;
    }

    @Override
    public Vector getNormal(Point point_on_body) {
        return super.getNormal(point_on_body);
    }
}
