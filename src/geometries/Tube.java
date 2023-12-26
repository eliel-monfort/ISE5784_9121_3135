package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry{
    protected Ray axis;

    public Tube(double _radius, Ray _axis){
        super(_radius);
        this.axis = _axis;
    }

    @Override
    public Vector getNormal(Point point_on_body) {
        return null;
    }
}
