package primitives;

public class Ray {
    final Point head;
    final Vector direction;

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
