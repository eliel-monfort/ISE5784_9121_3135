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
    abstract public List<Point> findIntersections(Ray ray);

    abstract public List<GeoPoint> findGeoIntersections(Ray ray);

    abstract protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}