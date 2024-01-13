package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of geometric objects that implement the Intersectable interface.
 * It allows combining multiple geometric objects and finding intersections with a given Ray.
 */
public class Geometries implements Intersectable {

    // The list of geometric objects in this collection.
    private List<Intersectable> geometries = null;

    /**
     * Default constructor for Geometries.
     * Initializes an empty list of geometries.
     */
    public Geometries() { }

    /**
     * Parameterized constructor for Geometries, allowing initialization with an array of Intersectable objects.
     *
     * @param geometries The array of geometric objects to be added to the collection.
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>();
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

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (this.geometries == null){
            return null;
        }
        List<Point> intersectables = null;
        for (Intersectable geometry : this.geometries){
            if (geometry.findIntersections(ray) != null){
                if (intersectables == null){
                    intersectables = new LinkedList<>();
                }
                intersectables.addAll(geometry.findIntersections(ray));
            }
        }
        return intersectables;
    }
}
