package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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

    /**
     * Helper method to find geometric intersections between the ray and the triangle, considering a specified maximum distance.
     *
     * @param ray The ray for which to find geometric intersections.
     * @param maxDistance The maximum distance for intersection detection.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the triangle,
     *         or null if no intersections are found.
     * @throws IllegalArgumentException if the denominator in the intersection calculation is zero.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (plane.findGeoIntersections(ray, maxDistance) == null)
            return null;
        Vector v1 = this.vertices.get(0).subtract(ray.getHead());
        Vector v2 = this.vertices.get(1).subtract(ray.getHead());
        Vector v3 = this.vertices.get(2).subtract(ray.getHead());
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        if ((ray.getDirection().dotProduct(n1) > 0 && ray.getDirection().dotProduct(n2) > 0 && ray.getDirection().dotProduct(n3) > 0)
                || (ray.getDirection().dotProduct(n1) < 0 && ray.getDirection().dotProduct(n2) < 0 && ray.getDirection().dotProduct(n3) < 0)) {
            double numerator = this.plane.getNormal().dotProduct(this.plane.getq().subtract(ray.getHead()));
            double denominator = this.plane.getNormal().dotProduct(ray.getDirection());
            if (isZero(denominator)) {
                throw new IllegalArgumentException("denominator cannot be zero");
            }
            double t = alignZero(numerator / denominator);
            if (t == 0) {
                return null;
            }
            if (t > 0) {
                return List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }
        return null;
    }
}