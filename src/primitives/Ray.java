package primitives;

import geometries.Intersectable;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import geometries.Intersectable.GeoPoint;

/**
 * A class that represents a ray in three-dimensional space,
 * defined by a starting point (head) and a direction vector.
 * The class use a Point object and a Vector object.
 */
public class Ray {
    /**
     * The starting point of the ray.
     */
    private final Point head;

    /**
     * The direction vector of the ray, normalized to have unit length.
     */
    private final Vector direction;

    private static final double DELTA = 0.1;


    /**
     * Constructs a new Ray with the specified head (starting point) and direction vector.
     * The direction vector is normalized to have unit length.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction){
        this.head = new Point(head.xyz);
        this.direction = new Vector(direction.normalize().xyz);
    }

    /**
     * Constructs a ray with a specified head (starting point), direction, and surface normal.
     * Adjusts the head slightly to avoid self-intersections due to numerical precision.
     *
     * @param head The starting point of the ray.
     * @param direction The direction vector of the ray.
     * @param normal The surface normal vector at the starting point.
     */
    public Ray(Point head, Vector direction, Vector normal){
        double nv = alignZero(direction.dotProduct(normal));
        if (nv > 0) {
            this.head = head.add(normal.scale(DELTA));
        }
        else if (nv < 0) {
            this.head = head.add((normal.scale(-DELTA)));
        }
        else{
            this.head = head;
        }
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Retrieves the direction vector of the Ray.
     *
     * @return The direction vector of the Ray.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Retrieves the head point of the Ray.
     *
     * @return The head point of the Ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Calculates a point along the line defined by this vector with respect to the parameter t.
     * If t is zero, the method returns the starting point of the line.
     *
     * @param t The parameter determining the position along the line (0 corresponds to the starting point).
     * @return A Point representing the position along the line at the specified parameter t.
     */
    public Point getPoint(double t)
    {
        if(isZero(t)){
            return head;
        }
        return this.getHead().add(this.getDirection().scale(t));
    }

    /**
     * Finds the closest Point to the head Point of the current object within a given list of Points.
     *
     * @param points The list of Points to search for the closest Point.
     * @return The closest Point to the head Point, or null if the list is empty.
     */
    public Point findClosestPoint(List<Point> points){
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest GeoPoint from a list of GeoPoint objects to a specified point.
     *
     * @param ls The list of GeoPoint objects to search through.
     * @return The closest GeoPoint to the specified point, or null if the list is empty.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> ls) {
        if (ls != null) {
            GeoPoint result = ls.get(0);
            for (GeoPoint point : ls) {
                if (point.point.distance(this.head) < result.point.distance(this.head)) {
                    result = point;
                }
            }
            return result;
        }
        return null;
    }
}