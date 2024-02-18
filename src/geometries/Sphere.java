package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A class that represents a sphere in three-dimensional space, defined by a radius and a center point.
 * The class inherits from the RadialGeometry class.
 */
public class Sphere extends RadialGeometry{

    /** The center point of the sphere. */
    private final Point center;

    /**
     * Constructs a new Sphere with the specified radius and center point.
     *
     * @param _radius The radius of the sphere.
     * @param _point  The center point of the sphere.
     */
    public Sphere(double _radius, Point _point){
        super(_radius);
        this.center = _point;
    }

    /**
     * Computes the normal vector to the sphere at the specified point.
     *
     * @param point_on_body The point on the sphere.
     * @return The normal vector to the sphere at the specified point.
     */
    @Override
    public Vector getNormal(Point point_on_body) {
        return point_on_body.subtract(center).normalize();
    }

    /**
     * Helper method to find geometric intersections between the ray and the sphere, considering a specified maximum distance.
     *
     * @param ray The ray for which to find geometric intersections.
     * @param maxDistance The maximum distance for intersection detection.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the sphere,
     *         or null if no intersections are found.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (this.center.equals(ray.getHead())) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }
        Vector u = this.center.subtract(ray.getHead());
        double Tm = alignZero(ray.getDirection().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - (Tm * Tm)));
        if (d >= this.radius) {
            return null;
        }
        double Th = alignZero(Math.sqrt((this.radius * this.radius) - (d * d)));
        if (Th * Th <= 0) {
            return null;
        }
        double t1 = alignZero(Tm + Th);
        double t2 = alignZero(Tm - Th);
        boolean dT1 = alignZero(t1 - maxDistance) <= 0;
        boolean dT2 = alignZero(t2 - maxDistance) <= 0;
        if (t1 > 0 && t2 > 0 && dT1 && dT2) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        } else if (t1 > 0 && t2 <= 0 && dT1) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        } else if (t1 <= 0 && t2 > 0 && dT2) {
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        } else {
            return null;
        }
    }
}