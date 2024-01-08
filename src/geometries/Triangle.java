package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * A class that represents a triangle in three-dimensional space defined by three vertices.
 * The class inherits from the Polygon class.
 */
public class Triangle extends Polygon{
    /**
     * Constructs a new Triangle with three specified vertices.
     *
     * @param point1 The first vertex of the triangle.
     * @param point2 The second vertex of the triangle.
     * @param point3 The third vertex of the triangle.
     */
    public Triangle(Point point1, Point point2, Point point3){
        super(point1, point2, point3);
    }

    //###########################################################################
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
    //###########################################################################
}