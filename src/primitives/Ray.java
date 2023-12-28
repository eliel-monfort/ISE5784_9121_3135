package primitives;

/**
 * A class that represents a ray in three-dimensional space,
 * defined by a starting point (head) and a direction vector.
 */
public class Ray {
    /**
     * The starting point of the ray.
     */
    final Point head;

    /**
     * The direction vector of the ray, normalized to have unit length.
     */
    final Vector direction;

    /**
     * Constructs a new Ray with the specified head (starting point) and direction vector.
     * The direction vector is normalized to have unit length.
     *
     * @param _head      The starting point of the ray.
     * @param _direction The direction vector of the ray.
     */
    public Ray(Point _head, Vector _direction){
        this.head = new Point(_head.xyz);
        this.direction = new Vector(_direction.normalize().xyz);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
