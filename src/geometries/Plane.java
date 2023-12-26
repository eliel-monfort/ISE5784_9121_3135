package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane extends Geometry{
    final private Point q;
    final private Vector normal;

    public Plane(Point point1, Point point2, Point point3){
        this.q = point1;
        this.normal = null;
    }

    public Plane(Point _point, Vector _normal){
        this.q = _normal;
        this.normal = _normal.normalize();
    }

    public Vector getNormal(){
        return this.normal;
    }

    @Override
    public Vector getNormal(Point point_on_body) {
        return this.normal;
    }
}
