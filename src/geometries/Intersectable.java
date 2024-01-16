package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface represents an object that can be intersected by a ray,
 * providing a method to find the intersections between the object and a given ray.
 */
public interface Intersectable {

    /**
     * Finds the intersections between the current object and a given ray.
     *
     * @param ray The ray for which intersections need to be found.
     * @return A list of Point objects representing the intersections between the ray and the object.
     */
    List<Point> findIntersections(Ray ray);
}
