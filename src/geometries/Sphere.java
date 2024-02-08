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
     * Finds the geometric intersections between the sphere and a given ray.
     *
     * @param ray The ray for which geometric intersections need to be found.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the sphere.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (center.equals(ray.getHead())) {
            return List.of(new GeoPoint(this, center.add(ray.getDirection().scale(radius))));
        }
        Vector u = center.subtract(ray.getHead());
        double Tm = ray.getDirection().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared() - (Tm * Tm));
        if (d >= this.radius) {
            return null;
        }
        double Th = Math.sqrt((this.radius * this.radius) - (d * d));
        if (Th * Th <= 0) {
            return null;
        }
        double t1 = alignZero(Tm + Th);
        double t2 = alignZero(Tm - Th);
        if (t1 > 0 && t2 > 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        } else if (t1 > 0 && t2 <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        } else if (t1 <= 0 && t2 > 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        } else {
            return null;
        }
    }

}