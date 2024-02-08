package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface represents an object that can be intersected by a ray,
 * providing a method to find the intersections between the object and a given ray.
 */
abstract public class Intersectable {

    /**
     * A nested class representing a geometric with a point of this geometry.
     */
    public static class GeoPoint {
        public Geometry geometry;
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
     * Finds the geometric intersections between the current object and a given ray.
     *
     * @param ray The ray for which geometric intersections need to be found.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the object.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray){
        return this.findGeoIntersectionsHelper(ray);
    }

    /**
     * A helper method to be implemented by subclasses for finding geometric intersections.
     *
     * @param ray The ray for which geometric intersections need to be found.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the object.
     */
    abstract protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}