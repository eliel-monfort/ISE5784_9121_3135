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
     * Finds intersection points between the given ray and the geometric objects in this collection.
     *
     * @param ray The ray for which intersections are to be found.
     * @return A list of intersection points between the ray and the geometric objects,
     *         or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (this.geometries == null){
            return null;
        }
        List<GeoPoint> intersectables = null;
        for (Intersectable geometry : this.geometries){
            List<GeoPoint> points = geometry.findGeoIntersections(ray);
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