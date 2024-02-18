package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of geometric objects that implement the Intersectable interface.
 * It allows combining multiple geometric objects and finding intersections with a given Ray.
 */
public class Geometries extends Intersectable {

    // The list of geometric objects in this collection.
    private List<Intersectable> geometries = new LinkedList<>();

    /**
     * Default constructor for Geometries.
     * Initializes an empty list of geometries.
     */
    public Geometries() {}

    /**
     * Parameterized constructor for Geometries, allowing initialization with an array of Intersectable objects.
     *
     * @param geometries The array of geometric objects to be added to the collection.
     */
    public Geometries(Intersectable... geometries) {
        this.add(geometries);
    }

    /**
     * Adds one or more geometric objects to the collection.
     *
     * @param geometries The array of geometric objects to be added to the collection.
     */
    public void add(Intersectable... geometries) {
        for (Intersectable item : geometries) {
            this.geometries.add(item);
        }
    }

    /**
     * Helper method to find intersections between the ray and geometries within this group,
     * considering a specified maximum distance for intersection detection.
     *
     * @param ray The ray for which to find intersections.
     * @param maxDistance The maximum distance for intersection detection.
     * @return A list of GeoPoints representing the intersections, or null if no intersections are found.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (this.geometries == null){
            return null;
        }
        List<GeoPoint> intersectables = null;
        for (Intersectable geometry : this.geometries){
            List<GeoPoint> points = geometry.findGeoIntersections(ray, maxDistance);
            if (points != null){
                if (intersectables == null){
                    intersectables = new LinkedList<>();
                }
                intersectables.addAll(points);
            }
        }
        return intersectables;
    }
}