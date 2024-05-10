package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;
import static java.lang.Float.POSITIVE_INFINITY;

/**
 * Intersectable interface represents an object that can be intersected by a ray,
 * providing a method to find the intersections between the object and a given ray.
 */
abstract public class Intersectable {

    /**
     * A nested class representing a geometric with a point of this geometry.
     */
    public static class GeoPoint {

        /** The Geometry object associated with this GeoPoint. */
        public Geometry geometry;

        /** The Point object representing the geometric point. */
        public Point point;

        /**
         * Constructs a GeoPoint with the specified geometry and point.
         *
         * @param geometry The Geometry object associated with this GeoPoint.
         * @param point    The Point object representing the geometric point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof GeoPoint other)
                    && this.geometry.equals(other.geometry)
                    && this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    /**
     * Finds the intersections between the current object and a given ray.
     *
     * @param ray The ray for which intersections need to be found.
     * @return A list of Point objects representing the intersections between the ray and the object.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersections between the current object and a given ray, up to a specified maximum distance.
     *
     * @param ray The ray for which intersections need to be found.
     * @param maxDistance The maximum distance for intersection detection.
     * @return A list of Point objects representing the intersections between the ray and the object.
     */
    public List<Point> findIntersections(Ray ray, double maxDistance) {
        var geoList = findGeoIntersections(ray, maxDistance);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the geometric intersections between the current object and a given ray.
     * Uses the default maximum distance (positive infinity) for intersection detection.
     *
     * @param ray The ray for which geometric intersections need to be found.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the object,
     *         or null if no intersections are found.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return this.findGeoIntersectionsHelper(ray, POSITIVE_INFINITY);
    }

    /**
     * Finds the geometric intersections between the current object and a given ray, up to a specified maximum distance.
     *
     * @param ray The ray for which geometric intersections need to be found.
     * @param maxDistance The maximum distance for intersection detection.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the object.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Helper method to find geometric intersections between the ray and the object, considering a specified maximum distance.
     *
     * @param ray The ray for which to find geometric intersections.
     * @param maxDistance The maximum distance for intersection detection.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the object.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}