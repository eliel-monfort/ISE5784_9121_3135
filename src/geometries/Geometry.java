package geometries;

import primitives.Point;
import primitives.Vector;

abstract public class Geometry {
    abstract public Vector getNormal(Point point_on_body);
}
