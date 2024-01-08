package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public interface Intersectable {

    /**
     * Finds the intersections between the current object and a given ray.
     *
     * @param ray The ray for which intersections need to be found.
     * @return A list of Point objects representing the intersections between the ray and the object.
     */
    List<Point> findIntersections(Ray ray);
}
